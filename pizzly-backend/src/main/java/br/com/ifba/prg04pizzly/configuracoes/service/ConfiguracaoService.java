package br.com.ifba.prg04pizzly.configuracoes.service;

import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoRequestDTO;
import br.com.ifba.prg04pizzly.configuracoes.dto.ConfiguracaoResponseDTO;
import br.com.ifba.prg04pizzly.configuracoes.entity.Configuracao;
import br.com.ifba.prg04pizzly.configuracoes.repository.ConfiguracaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ifba.prg04pizzly.logs.service.LogAuditoriaIService;

import java.math.BigDecimal;

//Implementação das regras de negócio de configuração
@Service
@RequiredArgsConstructor
public class ConfiguracaoService implements ConfiguracaoIService {

    // repositório responsável pela persistência das configurações
    private final ConfiguracaoRepository configuracaoRepository;

    // service responsável pelo registro de logs administrativos
    private final LogAuditoriaIService logAuditoriaService;

    public ConfiguracaoResponseDTO buscar() {
        // busca a configuração principal ou cria uma configuração padrão caso ainda não exista
        Configuracao configuracao = configuracaoRepository.findById(1L)
                .orElseGet(this::criarConfiguracaoPadrao);

        return converterParaResponseDTO(configuracao);
    }

    @Transactional
    public ConfiguracaoResponseDTO atualizar(
            ConfiguracaoRequestDTO dto,
            Long funcionarioId) {

        // busca a configuração principal ou cria uma configuração padrão caso ainda não exista
        Configuracao configuracao = configuracaoRepository.findById(1L)
                .orElseGet(this::criarConfiguracaoPadrao);

        // atualiza os dados gerais da pizzaria
        configuracao.setNomePizzaria(dto.getNomePizzaria());
        configuracao.setWhatsapp(dto.getWhatsapp());
        configuracao.setEndereco(dto.getEndereco());
        configuracao.setTaxaEntrega(dto.getTaxaEntrega());
        configuracao.setTempoEntrega(dto.getTempoEntrega());
        configuracao.setAberta(dto.getAberta());

        Configuracao atualizada = configuracaoRepository.save(configuracao);

        // registra a alteração das configurações no histórico de auditoria
        logAuditoriaService.registrar(
                funcionarioId,
                "EDITAR",
                "CONFIGURACOES",
                atualizada.getId(),
                "Configurações da pizzaria atualizadas"
        );

        return converterParaResponseDTO(atualizada);
    }

    //cria uma configuração padrão caso ainda não exista registro no banco
    private Configuracao criarConfiguracaoPadrao() {
        Configuracao configuracao = new Configuracao();

        configuracao.setNomePizzaria("Pizzly");
        configuracao.setWhatsapp("(74) 99999-9999");
        configuracao.setEndereco("Centro - Irecê/BA");
        configuracao.setTaxaEntrega(new BigDecimal("6.00"));
        configuracao.setTempoEntrega("30 - 45 min");
        configuracao.setAberta(true);

        return configuracaoRepository.save(configuracao);
    }

    //vai converte entidade para DTO de resposta
    private ConfiguracaoResponseDTO converterParaResponseDTO(Configuracao configuracao) {
        ConfiguracaoResponseDTO dto = new ConfiguracaoResponseDTO();

        dto.setId(configuracao.getId());
        dto.setNomePizzaria(configuracao.getNomePizzaria());
        dto.setWhatsapp(configuracao.getWhatsapp());
        dto.setEndereco(configuracao.getEndereco());
        dto.setTaxaEntrega(configuracao.getTaxaEntrega());
        dto.setTempoEntrega(configuracao.getTempoEntrega());
        dto.setAberta(configuracao.getAberta());

        return dto;
    }
}