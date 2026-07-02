package br.com.ifba.prg04pizzly.itenspedido.service;

import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoRequestDTO;
import br.com.ifba.prg04pizzly.itenspedido.dto.ItemPedidoResponseDTO;
import br.com.ifba.prg04pizzly.itenspedido.entity.ItemPedido;
import br.com.ifba.prg04pizzly.itenspedido.repository.ItemPedidoRepository;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.pedidos.repository.PedidoRepository;
import br.com.ifba.prg04pizzly.produtos.entity.Produto;
import br.com.ifba.prg04pizzly.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

//implementação das regras de negócio de ItemPedido
@Service
@RequiredArgsConstructor
public class ItemPedidoService implements ItemPedidoIService {

    // repositório responsável pelos itens
    private final ItemPedidoRepository itemPedidoRepository;

    // repositório responsável pelos pedidos
    private final PedidoRepository pedidoRepository;

    // repositório responsável pelos produtos
    private final ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public ItemPedidoResponseDTO save(ItemPedidoRequestDTO itemDTO) {

        // busca o pedido ao qual o item será vinculado
        Pedido pedido = pedidoRepository.findById(itemDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // busca o produto escolhido para compor o item
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado"));

        // cria o item e calcula o subtotal com base na quantidade e no preço informado
        ItemPedido item = new ItemPedido();

        item.setQuantidade(itemDTO.getQuantidade());

        // usa o preço do momento da compra, preservando tamanho, promoção ou variação do produto
        item.setPrecoUnitario(itemDTO.getPrecoUnitario());

        item.setSubtotal(
                itemDTO.getPrecoUnitario().multiply(
                        BigDecimal.valueOf(itemDTO.getQuantidade())
                )
        );

        item.setPedido(pedido);
        item.setProduto(produto);

        ItemPedido salvo = itemPedidoRepository.save(item);

        return converterParaResponseDTO(salvo);
    }

    @Override
    public Page<ItemPedidoResponseDTO> findAll(Pageable pageable) {

        // busca todos os itens de forma paginada e converte para DTO de resposta
        return itemPedidoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public ItemPedidoResponseDTO findById(Long id) {

        // busca o item pelo id ou lança exceção caso não exista
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item do pedido não encontrado")); //exceção caso não exista

        return converterParaResponseDTO(item);
    }

    @Override
    public List<ItemPedidoResponseDTO> findByPedidoId(Long pedidoId) {

        // busca todos os itens vinculados ao pedido informado
        return itemPedidoRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public ItemPedidoResponseDTO update(
            Long id,
            ItemPedidoRequestDTO itemDTO) {

        // busca o item que será atualizado
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item do pedido não encontrado"));

        // busca o produto para obter o preço atualizado
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Produto não encontrado"));

        // atualiza quantidade, preço unitário e subtotal do item
        item.setQuantidade(itemDTO.getQuantidade());
        item.setPrecoUnitario(produto.getPreco());
        item.setSubtotal(
                produto.getPreco().multiply(
                        BigDecimal.valueOf(itemDTO.getQuantidade())
                )
        );

        ItemPedido atualizado = itemPedidoRepository.save(item);

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca o item antes de remover para garantir que ele existe
        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item do pedido não encontrado"));

        itemPedidoRepository.delete(item);
    }

    //vai converte entidade para DTO de resposta
    private ItemPedidoResponseDTO converterParaResponseDTO(
            ItemPedido item) {

        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();

        dto.setId(item.getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());

        // adiciona os dados do pedido e do produto vinculados ao item
        dto.setPedidoId(item.getPedido().getId());

        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getNome());

        return dto;
    }
}