package com.lauriavictor.dao;

import com.lauriavictor.entidade.Usuario;
import com.lauriavictor.exception.ErroSistema;
import com.lauriavictor.util.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements InterfaceUsuario<Usuario> {
    
    @Override
    public void cadastrar(Usuario usuario) throws ErroSistema {
        try {
            Connection conn = FabricaConexao.getCon();
            PreparedStatement ps;
            if(usuario.getId() == null) { 
             ps = conn.prepareStatement("INSERT INTO `usuario` (`usuariologin`, `senha`, `nome`, `email`, `telefone`) VALUES (?, ?, ?, ?, ?);");
            } else {
                ps = conn.prepareStatement("UPDATE usuario SET usuariologin=?, senha=?, nome=?, email=?, telefone=? WHERE idusuario=?");
                ps.setInt(6, usuario.getId());
            } 
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getNome());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getFone());
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar o usuário.", ex);
        }
    }
    
    @Override
    public List<Usuario> buscar() throws ErroSistema {
        try {
            Connection conn = FabricaConexao.getCon();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario");
            ResultSet rs = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("idusuario"));
                usuario.setUsuario(rs.getString("usuariologin"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFone(rs.getString("telefone"));
                usuarios.add(usuario);
            }
            FabricaConexao.fecharConexao();
            return usuarios;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao listar usuários.", ex);
        }
    }
    
    @Override
    public void excluir(Usuario usuario) throws ErroSistema {
        try {
            Connection conn = FabricaConexao.getCon();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM usuario WHERE idusuario=?");
            ps.setInt(1, usuario.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover usuário.", ex);
        }
    }
}
