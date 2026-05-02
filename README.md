# FastTripPlanner

Aplicativo Android desenvolvido em Kotlin com Jetpack Compose para planejamento rápido de viagens.

O FastTripPlanner permite que o usuário informe os dados básicos de uma viagem, selecione opções de hospedagem e serviços adicionais, e visualize um resumo final com o cálculo do custo total estimado.

## Vídeo
Link para o vídeo do aplicativo: https://youtube.com/shorts/Ivc8rNKgWgQ

## Objetivo

O objetivo do projeto é aplicar conceitos fundamentais de desenvolvimento Android, incluindo múltiplas telas, navegação com Intents explícitas, passagem de dados entre Activities, gerenciamento de estado, validação de campos e uso de componentes básicos de interface.

## Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Intents explícitas
- Serializable
- rememberSaveable
- minSdk 26

## Funcionalidades

O aplicativo possui três telas principais:

1. Tela de Dados da Viagem
2. Tela de Opções da Viagem
3. Tela de Resumo da Viagem

O fluxo de navegação é:

TripDataActivity -> TripOptionsActivity -> TripSummaryActivity

## Tela 1 — Dados da Viagem

A primeira tela permite que o usuário informe os dados básicos da viagem.

Campos disponíveis:

- Destino
- Número de dias
- Orçamento diário

Validações implementadas:

- O destino é obrigatório.
- O destino não pode conter somente números.
- O número de dias é obrigatório.
- O número de dias deve ser numérico.
- O número de dias deve ser maior que zero.
- O orçamento diário é obrigatório.
- O orçamento diário deve ser numérico.
- O orçamento diário deve ser maior que zero.

Após preencher os dados corretamente, o usuário pode avançar para a segunda tela.

## Tela 2 — Opções da Viagem

A segunda tela permite configurar as opções da viagem.

O usuário deve escolher um tipo de hospedagem:

- Econômica
- Conforto
- Luxo

A seleção de hospedagem é feita com RadioButton.

O usuário também pode escolher serviços adicionais:

- Alimentação inclusa
- Transporte incluso
- Passeios inclusos

A seleção dos serviços é feita com Checkbox.

A tela possui dois botões:

- Voltar: retorna para a tela anterior.
- Calcular: envia os dados para a tela de resumo.

Antes de avançar, o aplicativo verifica se uma opção de hospedagem foi selecionada.

## Tela 3 — Resumo da Viagem

A terceira tela exibe o resumo completo da viagem.

Dados exibidos:

- Destino
- Número de dias
- Orçamento diário
- Tipo de hospedagem
- Alimentação inclusa
- Transporte incluso
- Passeios inclusos
- Valor total da viagem

A tela também possui um botão para reiniciar o planejamento e voltar para a primeira tela.

## Regras de cálculo

O custo base da viagem é calculado da seguinte forma:

custoBase = dias * orçamentoDiário * multiplicadorHospedagem

Multiplicadores de hospedagem:

| Tipo de hospedagem | Multiplicador |
|---|---:|
| Econômica | 1.0 |
| Conforto | 1.5 |
| Luxo | 2.2 |

Valores dos serviços adicionais:

| Serviço | Valor |
|---|---:|
| Transporte | R$ 300,00 fixo |
| Alimentação | R$ 50,00 por dia |
| Passeios | R$ 120,00 por dia |

O valor final é calculado somando o custo base com os serviços selecionados.

Fórmula geral:

total = custoBase + extrasSelecionados

## Passagem de dados entre telas

A navegação entre as telas é feita com Intents explícitas.

Os dados são enviados entre Activities usando putExtra.

Foi utilizado Serializable para transportar objetos entre telas. Embora Parcelable seja mais recomendado em Android por questões de performance, Serializable atende ao escopo deste projeto, pois os dados são simples e pequenos.

## Gerenciamento de estado

O aplicativo utiliza rememberSaveable para preservar o estado dos campos e seleções quando a tela é recriada, por exemplo, durante a rotação do dispositivo.

Isso foi aplicado em campos de texto, RadioButtons e Checkboxes.

## Como executar o projeto

1. Clone ou baixe este repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Execute o aplicativo em um emulador ou dispositivo físico com Android 8.0 ou superior.

## Como usar o aplicativo

1. Na primeira tela, informe o destino, o número de dias e o orçamento diário.
2. Clique no botão para avançar.
3. Na segunda tela, selecione o tipo de hospedagem.
4. Marque os serviços adicionais desejados.
5. Clique em Calcular.
6. Na terceira tela, visualize o resumo da viagem e o valor total.
7. Clique em Reiniciar para começar um novo planejamento.

## Observações

Durante o desenvolvimento, foi testada a utilização de Parcelable com Parcelize. Porém, devido a dificuldades de configuração no ambiente do projeto, foi mantido o uso de Serializable.

Essa decisão não compromete o funcionamento do aplicativo, pois os objetos enviados entre as telas são pequenos e simples.

## Autor

Nome: Raul Souza 
Prontuário: SC3045153
