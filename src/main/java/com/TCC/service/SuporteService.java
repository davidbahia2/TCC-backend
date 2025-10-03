        package com.TCC.service;

        import java.util.List;
        import java.util.Optional;

        import org.springframework.beans.factory.annotation.Autowired;

        import com.TCC.DTO.SuporteDTO;
        import com.TCC.model.Suporte;
        import com.TCC.repository.SuporteRepository;
        import org.springframework.stereotype.Service;

        @Service
        public class SuporteService {
            

        @Autowired
            private final SuporteRepository suporteRepository;

            public SuporteService (SuporteRepository suporteRepository){
                this.suporteRepository = suporteRepository;
            }





        public List<Suporte> listarTudo(){
            return suporteRepository.findAll();
        }

        public List<Suporte> buscarPorCidade(String cidade){
        return suporteRepository.procurarPorCidade(cidade);

        }

        public List<Suporte> buscarPorEspecialidade(String espcialidade){
            return suporteRepository.procurarPorEspecialidade(espcialidade);
        }

        public List<Suporte> buscarOsDois(String cidade, String especialidade){
            return suporteRepository.procurarPelosDois(cidade, especialidade);
        }

        public Suporte buscarPorId(Integer id){
            return suporteRepository.findById(id).orElseThrow(()-> new RuntimeException("suporte nao encontrado"+ id));
        }

        public Suporte CriarSuporte(Suporte sup){
            sup.setNome(sup.getNome());
            sup.setCidade(sup.getCidade());
            sup.setLocalizacao(sup.getLocalizacao());
            sup.setServicos(sup.getServicos());
            sup.setTelefone(sup.getTelefone());
            sup.setEspecialidade(sup.getEspecialidade());

            return suporteRepository.save(sup);
        }

        public Suporte atualizar(Suporte atualiza, int id){
        Optional<Suporte> supOpt = suporteRepository.findById(id);
        if(supOpt.isPresent()){
            Suporte suporteExiste = supOpt.get();
            suporteExiste.setCidade(atualiza.getCidade());
            suporteExiste.setEspecialidade(atualiza.getEspecialidade());
            suporteExiste.setNome(atualiza.getNome());
            suporteExiste.setServicos(atualiza.getServicos());
            suporteExiste.setTelefone(atualiza.getTelefone());
            suporteExiste.setEstado(atualiza.getEstado());

            return suporteRepository.save(suporteExiste);
        }
        throw new RuntimeException("nao encontramos o id para atualizar" + id);
        }

        public void deletar(Integer id){
            if(!suporteRepository.existsById(id)){
                throw new RuntimeException("nao foi possivel deletar pelo id"+ id);
            }
            suporteRepository.deleteById(id);
        }

        private SuporteDTO convertToDTO(Suporte suporte) {
                return new SuporteDTO(
                    suporte.getId(),
                    suporte.getNome(),
                    suporte.getEspecialidade(),
                    suporte.getCidade(),
                    suporte.getEstado(),
                    suporte.getTelefone(),
                    suporte.getServicos()
                );
        }

        private Suporte convertToModel(SuporteDTO dto) {
                return new Suporte(
                    dto.getNome(),
                    dto.getEspecialidade(),
                    dto.getCidade(),
                    dto.getEstado(),
                    dto.getTelefone(),
                    dto.getServicos()
                );
        }

        }