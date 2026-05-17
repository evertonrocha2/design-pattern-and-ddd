# 3. Bounded Contexts

Cada subdomínio recebe seu próprio bounded context. Um mesmo conceito do negócio pode aparecer em mais de um contexto, mas cada lado enxerga aquilo do seu jeito. Pedido de frete, em Gestão de Fretes, é cotação, modal e rota. O mesmo pedido em Rastreamento é carga em trânsito com posição. Em Faturamento, ele vira um CT-e e um valor a receber. Não tem problema o mesmo objeto do mundo real ter três modelos diferentes no sistema. O problema é tentar forçar um modelo só para servir aos três.

## Contexto de Gestão de Fretes

É o coração da operação. Cuida da cotação, da criação do pedido de frete, da escolha do modal, do cálculo da rota e da alocação de veículo e motorista. Aqui Pedido significa solicitação de transporte aceita pelo cliente. Este contexto não precisa saber nada sobre detalhes fiscais nem sobre a ficha de manutenção do veículo. Quando o pedido é criado, ele avisa o resto do mundo através do evento PedidoCriado. Quando aloca um veículo, publica VeiculoAlocado.

## Contexto de Rastreamento

Fica escutando os eventos de geolocalização que chegam dos veículos. A partir daí, atualiza a posição da carga, calcula o tempo estimado de chegada, detecta desvio de rota e marca a entrega como concluída quando a regra de chegada é satisfeita. Aqui o conceito central não é Pedido, é Carga em trânsito. O pedido aparece só como uma referência por identificador. Esse contexto começa a trabalhar quando recebe o PedidoCriado vindo de Fretes, e termina o trabalho publicando EntregaConfirmada.

## Contexto de Faturamento

Entra em cena quando a entrega é confirmada. Gera o CT-e, emite a nota fiscal, registra o recebimento e faz a conciliação com o extrato do banco. Não conhece rota nem cálculo de modal. Aqui o que importa é o valor cobrado, o imposto retido, a fatura emitida e o pagamento conciliado. Recebe o evento EntregaConfirmada de Rastreamento para começar a trabalhar.

## Contexto de Manutenção de Frota

É dono do veículo. Faz o cadastro, planeja as revisões preventivas com base em quilometragem ou tempo, registra ordens de serviço corretivas e controla quando o veículo está disponível para operação. Não sabe nada sobre cliente, rota ou CT-e. Quando um veículo entra em manutenção, publica VeiculoIndisponivel para Fretes parar de tentar alocá-lo, e libera de novo com VeiculoDisponivel quando o serviço termina.

## Como esses contextos se falam

A relação entre eles é de cima para baixo, em cadeia. Fretes está no topo: ele cria o pedido e dispara o início de tudo. Rastreamento depende de receber o PedidoCriado para saber que tem uma nova carga para acompanhar, e depende também do VeiculoAlocado para saber qual placa associar à carga. Quando o rastreamento decide que a entrega aconteceu, ele avisa Faturamento, que então emite o CT-e. Faturamento é o último elo dessa cadeia e não devolve nada para os outros.

Manutenção fica em paralelo, conversando direto com Fretes. Quando um veículo precisa entrar em revisão, Manutenção avisa Fretes que aquela placa saiu da operação. Quando volta, avisa de novo. Manutenção também escuta o VeiculoAlocado para projetar quilometragem futura e antecipar a próxima revisão, mas isso é só leitura, não impõe nada de volta.

Toda essa conversa acontece por eventos assíncronos, publicados num barramento de mensagens. Cada contexto tem seu próprio banco e sua própria pilha. Quando precisa de algo que não tem, escuta o evento certo do contexto dono daquilo, ou faz uma chamada explícita por uma porta declarada no domínio. Nunca lê direto o banco do outro.
