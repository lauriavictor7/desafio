package com.lauriavictor.dao;

import com.lauriavictor.exception.ErroSistema;
import java.util.List;


public interface InterfaceUsuario<E> {
    
    public void cadastrar(E entidade) throws ErroSistema;
    public List<E> buscar() throws ErroSistema;
    public void excluir(E entidade) throws ErroSistema;
        
}
