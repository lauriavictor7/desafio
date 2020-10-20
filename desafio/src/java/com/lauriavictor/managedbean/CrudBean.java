package com.lauriavictor.managedbean;

import com.lauriavictor.dao.InterfaceUsuario;
import com.lauriavictor.exception.ErroSistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class CrudBean<E, D extends InterfaceUsuario> {

    private String estadoTela = "buscar";

    private E entidade;
    private List<E> entidades;

    public void novo() {
        entidade = CriarNovaEntidade();
        mudarParaInseri();
    }

    public void cadastrar() {
        try {
            getDao().cadastrar(entidade);
            entidade = CriarNovaEntidade();
            mensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
            mudarParaBusca();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            mensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(E entidade) {
        this.entidade = entidade;
        mudarParaEdita();
    }

    public void remover(E entidade) {
        try {
            getDao().excluir(entidade);
            entidades.remove(entidade);
            mensagem("Removido com sucesso!!", FacesMessage.SEVERITY_INFO);
            //mudarParaBuscar();
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            mensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void buscar() {
        if(!isBusca()) {
            mudarParaBusca();
            return;
        }
        try {
            entidades = getDao().buscar();
            if(entidades == null || entidades.size() < 1) {
               mensagem("Sem registros para exibir!", FacesMessage.SEVERITY_WARN); 
            }
        } catch (ErroSistema ex) {
            Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
            mensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void mensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<E> entidades) {
        this.entidades = entidades;
    }

    public abstract D getDao();

    public abstract E CriarNovaEntidade();

    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }

    public void mudarParaInseri() {
        estadoTela = "inserir";
    }

    public void mudarParaEdita() {
        estadoTela = "editar";
    }

    public void mudarParaBusca() {
        estadoTela = "buscar";
    }
}
