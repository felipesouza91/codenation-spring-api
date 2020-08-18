package br.com.felipe.codenationlog.infra.util;

public interface DtoDisassembler<X, Y> {

  public X toDomainObject(Y inputObject);
}