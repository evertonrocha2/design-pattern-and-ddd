# 5. Design Estratégico e Shared Kernel

Existe um equívoco comum em DDD que é jogar no shared kernel tudo que parece reutilizável. Shared kernel não é pasta de utilitários. É um conjunto pequeno de conceitos cuja mudança afeta todos os contextos ao mesmo tempo e por isso exige acordo entre os times responsáveis. Quanto menor o shared kernel, melhor.

## O que faz sentido compartilhar

Endereço é o caso mais óbvio. Aparece em Gestão de Fretes para indicar origem e destino do pedido, em Rastreamento para registrar onde a carga foi coletada e onde foi entregue, em Faturamento para guardar o endereço de cobrança e o endereço fiscal, e em Manutenção para registrar a oficina onde o veículo está sendo atendido. É um value object simples, com logradouro, número, bairro, cidade, estado e CEP, e validação de formato. Vale ter um só.

Dinheiro também. Está em Fretes na cotação, em Faturamento no valor cobrado, no imposto retido e no valor recebido, e em Manutenção no custo da ordem de serviço. Ter uma representação única que carrega valor e moeda, e que não deixa misturar moedas diferentes por engano em uma operação aritmética, evita uma classe inteira de bug bobo.

A Placa de veículo é um caso mais delicado, mas vale o esforço. O dono do agregado Veículo é Manutenção, e isso não muda. Mas a placa é referenciada por Fretes quando o sistema registra qual veículo foi alocado ao pedido, e por Rastreamento quando associa uma posição recebida ao veículo correto. Então a Placa vira um value object compartilhado que carrega o número e a validação do formato brasileiro. O resto do veículo, modelo, ano, ficha de manutenção, fica em Manutenção.

A identidade de pessoa e empresa segue a mesma lógica. CPF e CNPJ aparecem em Fretes como remetente e destinatário, em Faturamento como emitente e tomador, e em Manutenção como fornecedor de peças e serviços. Como os três contextos precisam validar dígito e armazenar o número formatado, vale ter Cpf e Cnpj no shared kernel.

Por fim, o identificador do pedido de frete. O pedido é criado em Fretes, mas Rastreamento referencia esse identificador para vincular os eventos de posição à carga certa, e Faturamento referencia para amarrar o CT-e ao pedido de origem. O shared kernel guarda apenas o identificador como value object, não o pedido inteiro. Cada contexto monta sua própria visão do pedido a partir desse identificador, e essa é justamente a beleza do bounded context.

## O que parece compartilhável mas não é

Status da carga é tentador colocar no shared kernel, mas ele pertence a Rastreamento. Se Faturamento precisa saber que uma entrega aconteceu, ele se inscreve no evento EntregaConfirmada e cuida da própria interpretação dali pra frente. Compartilhar o enum de status criaria um acoplamento ruim, onde uma mudança em Rastreamento obrigaria todos os outros contextos a se atualizarem juntos.

Cliente também é um caso que parece óbvio mas não é. Em Fretes, Cliente é quem contrata o serviço e tem condições comerciais negociadas. Em Faturamento, Cliente é quem paga, com histórico de inadimplência e situação fiscal. São duas visões diferentes de uma mesma pessoa do mundo real. O que pode ser compartilhado é só o CNPJ que identifica essa pessoa, e isso já está coberto pelo value object Cnpj.

## Como os contextos se relacionam de fato

A relação principal é em cadeia. Fretes publica PedidoCriado e VeiculoAlocado. Rastreamento consome esses dois eventos para iniciar e configurar o monitoramento da carga. Quando a entrega acontece, Rastreamento publica EntregaConfirmada, e Faturamento consome esse evento para emitir o CT-e e abrir a fatura. Nessa cadeia, Fretes está sempre upstream em relação a Rastreamento, e Rastreamento está upstream em relação a Faturamento. As duas relações são clássicas de Customer/Supplier no vocabulário de DDD: quem está embaixo na cadeia depende de quem está em cima, e precisa se adaptar quando os eventos mudam.

Em paralelo, Manutenção mantém uma relação direta com Fretes. Quando um veículo precisa entrar em revisão, Manutenção publica VeiculoIndisponivel, e Fretes para de oferecer aquele veículo na alocação. Quando o serviço termina, Manutenção publica VeiculoDisponivel, e o veículo volta para a frota disponível. Manutenção também escuta o VeiculoAlocado de Fretes, mas só para projetar quilometragem futura e antecipar a próxima revisão preventiva. É um relacionamento de mão dupla, mas mediado totalmente por eventos, sem chamada síncrona de um lado para o outro.

Cada contrato de evento é versionado e documentado, porque é nele que a operação se sustenta no dia a dia. Quando alguém precisa adicionar um campo novo em um evento existente, isso precisa ser combinado com os consumidores, porque é exatamente aí que mudanças quebram silenciosamente sistemas em produção.
