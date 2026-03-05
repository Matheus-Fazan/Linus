package com.linus.exception.dao;

public class EntityNotFoundException extends RuntimeException {
  private final Object identificador;

  public EntityNotFoundException(Object identificador) {
    super(String.format("Acesso não encontrado(a) com identificador: %s", identificador));
    this.identificador = identificador;
  }


  public Object getIdentifier() {
    return identificador;
  }
}
