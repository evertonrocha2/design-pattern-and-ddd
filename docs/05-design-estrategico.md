# 5. Design Estratégico e Shared Kernel

Tem um erro clássico em DDD que é jogar no shared kernel tudo que parece reutilizável. Shared kernel não é pasta de utilitários. É um conjunto pequeno de conceitos que, se mudarem, afetam todos os contextos ao mesmo tempo e por isso exigem acordo entre os times. Quanto menor, melhor.

## O que faz sentido compartilhar

Endereço é o caso óbvio. Aparece em Fretes (origem e destino), em Rastreamento (onde foi coletado e entregue), em Faturamento (cobrança e emissão de CT-e) e em Manutenção (endereço da oficina). Value object simples, com logradouro, número, bairro, cidade, estado e CEP.

Dinheiro também. Está em Fretes na cotação, em Faturamento no valor cobrado e no imposto retido, e em Manutenção no custo da OS. Ter uma representação única que carrega valor e moeda, e que não deixa misturar moedas diferentes por engano, evita uma classe inteira de bug bobo.

A Placa do veículo é um caso mais delicado. O dono do agregado Veículo é Manutenção, e isso não muda. Mas a placa é referenciada por Fretes ao registrar qual veículo foi alocado, e por Rastreamento pra associar a posição ao veículo. Então a Placa vira value object compartilhado. O resto do veículo continua em Manutenção.

CPF e CNPJ entram pela mesma lógica. Aparecem em Fretes como remetente e destinatário, em Faturamento como emitente e tomador, e em Manutenção como fornecedor de peças. Todos validam dígito e armazenam o número formatado, então vale ter um value object só.

Por fim, o identificador do pedido de frete. O pedido nasce em Fretes, mas Rastreamento amarra os eventos de posição a ele, e Faturamento amarra o CT-e a ele. O shared kernel guarda só o identificador, não o pedido inteiro. Cada contexto monta sua visão do pedido a partir desse ID, e essa é justamente a beleza do bounded context.

## O que parece compartilhável mas não é

Status da carga é tentador, mas pertence a Rastreamento. Se Faturamento precisa saber que a entrega aconteceu, escuta o evento `EntregaConfirmada` e cuida da própria interpretação. Compartilhar o enum de status acoplaria os contextos.

Cliente também não vai. Em Fretes, Cliente é quem contrata e tem condição comercial. Em Faturamento, Cliente é quem paga e tem situação fiscal. São duas visões diferentes da mesma pessoa do mundo real. O que dá pra compartilhar é só o CNPJ, que já está coberto.

## Como os contextos se conectam

A relação principal é em cadeia. Fretes publica `PedidoCriado` e `VeiculoAlocado`. Rastreamento consome os dois pra iniciar e configurar o monitoramento. Quando a entrega acontece, Rastreamento publica `EntregaConfirmada`, e Faturamento usa esse evento pra emitir o CT-e. Nessa cadeia, Fretes está upstream de Rastreamento, e Rastreamento está upstream de Faturamento. Quem está embaixo se adapta a quem está em cima.

Em paralelo, Manutenção conversa direto com Fretes. Quando um veículo entra em revisão, publica `VeiculoIndisponivel` pra Fretes parar de oferecer aquela placa. Quando volta, publica `VeiculoDisponivel`. Manutenção também escuta `VeiculoAlocado`, mas só pra projetar quilometragem futura.

Os contratos de evento são versionados. Adicionar campo novo em evento existente precisa combinar com os consumidores, porque é exatamente aí que mudanças quebram silenciosamente em produção.
