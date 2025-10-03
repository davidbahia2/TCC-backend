package com.TCC.DTO;

import com.TCC.enums.TipoRespiracao;

public class IniciarSessaoDTO {
    
  private TipoRespiracao tipoRespiracao;
    private String usuarioId;
    
    // Construtor padrão
    public IniciarSessaoDTO() {}
    
    public TipoRespiracao getTipoRespiracao() { return tipoRespiracao; }
    public void setTipoRespiracao(TipoRespiracao tipoRespiracao) { this.tipoRespiracao = tipoRespiracao; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}