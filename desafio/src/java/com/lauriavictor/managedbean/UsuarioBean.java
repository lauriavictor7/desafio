package com.lauriavictor.managedbean;

import com.lauriavictor.dao.InterfaceUsuario;
import com.lauriavictor.dao.UsuarioDAO;
import com.lauriavictor.entidade.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class UsuarioBean extends CrudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    
    @Override
    public UsuarioDAO getDao() {
        if(usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario CriarNovaEntidade() {
        return new Usuario();
    }

    
}
