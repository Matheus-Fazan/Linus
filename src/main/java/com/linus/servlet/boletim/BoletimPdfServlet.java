package com.linus.servlet.boletim;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.linus.dao.NotaDao;
import com.linus.dto.GeraBoletimDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/aluno/boletim/pdf")
public class BoletimPdfServlet extends HttpServlet {

    private static final NotaDao dao = new NotaDao();

    private static final BaseColor COR_HEADER     = new BaseColor(196, 158, 230); // #C49EE6
    private static final BaseColor COR_LINHA_PAR  = new BaseColor(243, 243, 243); // #F3F3F3
    private static final BaseColor COR_BRANCO     = BaseColor.WHITE;
    private static final BaseColor COR_TEXTO      = new BaseColor(26, 26, 26);    // #1a1a1a

    private static final Font FONTE_TITULO  = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD,   COR_TEXTO);
    private static final Font FONTE_SUB     = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, COR_TEXTO);
    private static final Font FONTE_HEADER  = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   COR_BRANCO);
    private static final Font FONTE_CELULA  = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, COR_TEXTO);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            String cargoUsuario = (String) requestReponse.getSessionAttribute("perfilUsuario");
            String matriculaParam = requestReponse.getRequestParameter("matricula");
            Integer idUsuarioLogadoParam = (Integer) requestReponse.getSessionAttribute("idUsuario");
            System.out.println(idUsuarioLogadoParam);

            if (matriculaParam == null || matriculaParam.isBlank()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula não informada.");
                return;
            }

            int matricula;
            try {
                matricula = Integer.parseInt(matriculaParam);
            } catch (NumberFormatException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula inválida.");
                return;
            }

            if ("aluno".equalsIgnoreCase(cargoUsuario)) {
                if (idUsuarioLogadoParam == null) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este boletim.");
                    return;
                }

                try {
                    if (idUsuarioLogadoParam != matricula) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este boletim.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado. Você não tem permissão para acessar este boletim.");
                    return;
                }
            }

            List<GeraBoletimDto> boletim = dao.findByMatricula(matricula);

            if (boletim == null || boletim.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Nenhuma nota encontrada para este aluno.");
                return;
            }

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=\"boletim.pdf\"");

            gerarPdf(boletim, resp);

        } catch (SQLException | ConnectionException cause) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao consultar o servidor. Por favor, tente novamente.");
        } catch (DocumentException cause) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao gerar o PDF. Por favor, tente novamente.");
        } catch (IOException cause) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao enviar o PDF. Por favor, tente novamente.");
        }
    }

    /**
     * Gera o PDF do boletim.
     *
     * @param boletim lista de {@link GeraBoletimDto} com os dados do aluno
     * @param resp    objeto {@link HttpServletResponse} para escrita do PDF
     * @throws DocumentException caso ocorra erro na criação do documento iText
     * @throws IOException       caso ocorra erro de escrita na resposta
     */
    private void gerarPdf(List<GeraBoletimDto> boletim, HttpServletResponse resp) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, resp.getOutputStream());
        document.open();

        GeraBoletimDto primeiro = boletim.iterator().next();

        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[]{4, 1});

        Image logo = Image.getInstance(getServletContext().getRealPath("/assets/imgs/logo.png"));
        logo.scaleToFit(60, 60);

        PdfPCell logoCell = new PdfPCell(logo);
        logoCell.setBorder(PdfPCell.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        logoCell.setVerticalAlignment(Element.ALIGN_TOP);

        Paragraph titulo = new Paragraph("Instituto Linus", FONTE_TITULO);
        Paragraph subtitulo = new Paragraph(
                "Boletim Escolar",
                new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, COR_TEXTO)
        );

        PdfPCell textCell = new PdfPCell();
        textCell.setBorder(PdfPCell.NO_BORDER);

        textCell.addElement(titulo);
        textCell.addElement(subtitulo);

        header.addCell(textCell);
        header.addCell(logoCell);

        document.add(header);

        document.add(new Paragraph("Aluno: " + primeiro.getNomeAluno(), FONTE_SUB));
        document.add(new Paragraph("Turma: " + primeiro.getTurma(), FONTE_SUB));
        document.add(new Paragraph(" "));

        LineSeparator linha = new LineSeparator(1, 100, COR_HEADER, Element.ALIGN_CENTER, -2);
        document.add(new Chunk(linha));
        document.add(new Paragraph(" "));

        PdfPTable tabela = new PdfPTable(new float[]{3f, 1f, 1f, 1f, 1.5f, 3.5f});
        tabela.setWidthPercentage(100);
        tabela.setSpacingBefore(6);

        adicionarCabecalhoTabela(tabela);

        for (int i = 0; i < boletim.size(); i++) {
            GeraBoletimDto dto = boletim.get(i);
            BaseColor corLinha = (i % 2 == 0) ? COR_BRANCO : COR_LINHA_PAR;

            adicionarCelula(tabela, dto.getMateria(),                    corLinha, Element.ALIGN_LEFT);
            adicionarCelula(tabela, formatarNota(dto.getNota1()),         corLinha, Element.ALIGN_CENTER);
            adicionarCelula(tabela, formatarNota(dto.getNota2()),         corLinha, Element.ALIGN_CENTER);
            adicionarCelula(tabela, formatarNota(dto.getMedia()),         corLinha, Element.ALIGN_CENTER);
            adicionarCelula(tabela, dto.getSituacao(),                   corLinha, Element.ALIGN_CENTER);
            adicionarCelula(tabela, dto.getObservacao(),                 corLinha, Element.ALIGN_LEFT);
        }

        document.add(tabela);
        document.close();
    }

    private void adicionarCabecalhoTabela(PdfPTable tabela) {
        String[] colunas = {"Matéria", "N1", "N2", "Média", "Situação", "Observação"};
        for (String col : colunas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, FONTE_HEADER));
            cell.setBackgroundColor(COR_HEADER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            cell.setBorder(Rectangle.NO_BORDER);
            tabela.addCell(cell);
        }
    }

    private void adicionarCelula(PdfPTable tabela, String texto, BaseColor cor, int alinhamento) {
        PdfPCell cell = new PdfPCell(new Phrase(texto != null ? texto : "-", FONTE_CELULA));
        cell.setBackgroundColor(cor);
        cell.setHorizontalAlignment(alinhamento);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(7);
        cell.setBorder(Rectangle.NO_BORDER);
        tabela.addCell(cell);
    }

    private String formatarNota(double nota) {
        return String.format("%.2f", nota);
    }
}
