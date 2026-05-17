# 3. Bounded Contexts

Cada subdomínio recebe seu próprio bounded context. Um mesmo conceito do mundo real pode aparecer em mais de um contexto, mas cada lado enxerga aquilo do seu jeito. Pedido em Fretes é cotação, modal e rota. O mesmo pedido em Rastreamento é carga em trânsito com posição. Em Faturamento ele vira CT-e e valor a receber. Não tem problema o mesmo objeto ter três modelos diferentes no sistema. O problema é tentar forçar um modelo só pra servir aos três.

## Gestão de Fretes

Cuida da cotação, criação do pedido, escolha do modal, cálculo da rota e alocação de veículo e motorista. Aqui Pedido é solicitação de transporte aceita pelo cliente. Não sabe nada sobre detalhes fiscais nem ficha de manutenção. Publica `PedidoCriado` e `VeiculoAlocado`.

## Rastreamento

Escuta os eventos de geolocalização que chegam dos veículos, atualiza a posição da carga, calcula ETA e marca a entrega como concluída. Aqui o conceito central é Carga em trânsito, não Pedido. Pedido só aparece como referência. Começa o trabalho ao receber `PedidoCriado` e termina publicando `EntregaConfirmada`.

## Faturamento

Entra em cena quando a entrega é confirmada. Gera CT-e, emite a nota, registra recebimento e faz a conciliação com o extrato. Não conhece rota nem cálculo de modal. O que importa aqui é valor cobrado, imposto retido e pagamento. Consome `EntregaConfirmada` de Rastreamento.

## Manutenção de Frota

É dono do veículo. Cadastra, planeja revisões preventivas por quilometragem ou tempo, registra ordens de serviço corretivas e controla disponibilidade. Não sabe nada sobre cliente, rota ou CT-e. Publica `VeiculoIndisponivel` quando o veículo entra em revisão e `VeiculoDisponivel` quando volta.

## Como conversam

A relação é em cadeia. Fretes está no topo, cria o pedido e dispara tudo. Rastreamento depende de receber `PedidoCriado` pra começar o monitoramento, e do `VeiculoAlocado` pra associar a placa. Quando a entrega acontece, avisa Faturamento, que emite o CT-e. Faturamento é o último elo dessa cadeia e não devolve nada.

Manutenção fica em paralelo, conversando direto com Fretes. Quando um veículo entra em revisão, avisa Fretes pra parar de alocar. Quando volta, avisa de novo. Também escuta `VeiculoAlocado` pra projetar quilometragem futura, mas só pra leitura.

Toda essa conversa é por evento assíncrono. Cada contexto tem seu próprio banco. Quando precisa de algo que não tem, escuta o evento certo ou chama por uma porta declarada no domínio. Nunca lê direto o banco do outro.
