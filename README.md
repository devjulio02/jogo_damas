# Código Refatorado

Esta branch reorganiza o código sem alterar as regras ou as funcionalidades do jogo.  
A principal mudança foi reduzir as responsabilidades concentradas em `Jogo`, preservando os nomes e o fluxo dos métodos sempre que possível.

## Versão Original

### `Jogo`

- `moverPeca(...)`: coordenava a jogada e também verificava jogador, movimento, captura, continuação, turno e promoção.
- `simularMovimentoEValidar(...)`: percorria o caminho, verificava obstáculos, cores e armazenava as peças capturadas.
- `percorrerEVerificar(...)`: procurava capturas nas diagonais e usava exceção para controlar posições fora do tabuleiro.
- `deveContinuarJogando(...)`: verificava se a mesma peça deveria continuar após uma captura.
- `comerPecas()`: removia peças, atualizava pontos e reiniciava o contador sem captura.
- `podeTransformarParaDama(...)` e `transformarPedraParaDama(...)`: verificavam e executavam a promoção.
- `colocarPecas(...)`: posicionava as peças iniciais no tabuleiro.
- `trocarDeVez()` e `getGanhador()`: controlavam turno e vencedor por códigos numéricos.
- `toString()`: montava o texto exibido pelo menu **Status** da interface.

A classe concentrava regras, validação, montagem, promoção e apresentação. Isso reduzia a coesão e caracterizava uma classe com responsabilidades excessivas.

### `Peca`

Definia o contrato das peças, mas também armazenava quatro códigos numéricos:

- `PEDRA_BRANCA`
- `DAMA_BRANCA`
- `PEDRA_VERMELHA`
- `DAMA_VERMELHA`

Os códigos misturavam dois conceitos diferentes: cor e tipo da peça.

### `Pedra` e `Dama`

- `Pedra` implementava `Peca` e concentrava estado e movimentação.
- `Dama` herdava de `Pedra`, apesar de ser outro tipo de peça.
- `isMovimentoValido(...)` era sobrescrito para alterar a regra da dama.

### `Casa`

Armazenava diretamente uma `Pedra`. Isso impedia que a casa dependesse apenas do contrato `Peca`.

### `Tabuleiro`

- `montarTabuleiro()`: criava a matriz de casas.
- `getCasa(...)`: devolvia uma posição.
- Mantinha constantes de tamanho e direção, enquanto o posicionamento e a promoção permaneciam em `Jogo`.

### `Jogador`

Armazenava nome e pontuação. A classe `Jogo` ainda precisava decidir, por códigos, se uma peça pertencia ao jogador atual.

### Classes GUI

- `JanelaPrincipal.reagir(...)`: recebia os cliques e chamava `Jogo.moverPeca(...)`.
- O menu **Status** chamava `jogo.toString()`.
- `TabuleiroGUI.atualizar(...)` escolhia as imagens usando os quatro códigos numéricos de tipo.
- `CasaGUI` representava visualmente cada casa e seus ícones.

## Versão Refatorada

### `Jogo`

Continua sendo o controlador da partida.

- `moverPeca(...)`: teve seu fluxo principal preservado, mas delega validação, posse da peça, posicionamento e promoção.
- `comerPecas()`: permaneceu porque altera pontuação e estado da partida.
- `trocarDeVez()` e `getGanhador()`: foram mantidos para preservar a identidade e o funcionamento original.

**Princípios:** GRASP Controller, SRP e baixo acoplamento.

### `ValidadorMovimento` — nova classe

Recebeu os métodos de validação que estavam em `Jogo`:

- `simularMovimentoEValidar(...)`
- `percorrerEVerificar(...)`
- `deveContinuarJogando(...)`
- `getPecasAComer()`

Os nomes foram preservados para manter a rastreabilidade entre as versões. A classe concentra percurso, obstáculos e capturas, enquanto `Jogo` apenas solicita o resultado.

As direções diagonais passaram a existir somente dentro do método que as utiliza, eliminando constantes estáticas desnecessárias.

**Princípios:** SRP, alta coesão, baixo acoplamento e GRASP Pure Fabrication.

### `Tabuleiro`

Além de manter as casas, passou a receber operações que dependem de posições e limites:

- `colocarPecas()`: era `Jogo.colocarPecas(Tabuleiro)` e agora pertence ao próprio tabuleiro.
- `transformarPedraParaDama(...)`: reúne a validação e a transformação que antes estavam em dois métodos de `Jogo`.
- `posicaoValida(...)`: verifica limites sem usar exceção como controle de fluxo.

Antes:

```java
colocarPecas(tabuleiro);
```

Depois:

```java
tabuleiro.colocarPecas();
```

O tabuleiro não precisa mais ser recebido como parâmetro por um método de outra classe.

**Princípios:** GRASP Especialista na Informação, SRP e alta coesão.

### `Peca`

Permaneceu como interface e continua definindo o contrato comum:

- `mover(...)`
- `isMovimentoValido(...)`
- `podeMoverSemCaptura(...)`
- `getCor()`
- `getTipo()`

Os quatro códigos numéricos foram removidos.

### `PecaAbstrata` — nova classe

Centraliza somente o estado e o comportamento realmente compartilhados:

- casa atual;
- cor;
- `mover(...)`;
- `getCor()`.

`Pedra` e `Dama` passam a herdar da mesma base, sem que `Dama` precise herdar de `Pedra`.

**Princípios:** reutilização por herança, redução de duplicação e LSP.

### `Pedra`

- Implementa sua própria regra em `isMovimentoValido(...)`.
- `podeMoverSemCaptura(...)` considera distância e sentido da cor.
- `getTipo()` retorna `TipoPeca.PEDRA`.

### `Dama`

- Implementa sua regra diagonal em `isMovimentoValido(...)`.
- Sobrescreve `podeMoverSemCaptura(...)`.
- `getTipo()` retorna `TipoPeca.DAMA`.

O polimorfismo ocorre quando o código trabalha com `Peca` e chama:

```java
peca.isMovimentoValido(destino);
```

A implementação executada depende de o objeto ser `Pedra` ou `Dama`.

**Princípios:** polimorfismo, OCP e LSP.

### `CorPeca` — nova enumeração

Representa apenas a cor da peça:

- `BRANCA`
- `VERMELHA`

Também informa o sentido normal do movimento. A cor deixou de ser misturada com o tipo da peça.

### `TipoPeca` — nova enumeração

Representa somente a categoria visual:

- `PEDRA`
- `DAMA`

É utilizada principalmente pela GUI para escolher a imagem correta, sem controlar as regras de movimento.

### `Casa`

Passou a armazenar `Peca` em vez de `Pedra`.

Antes:

```java
private Pedra peca;
```

Depois:

```java
private Peca peca;
```

A casa depende do contrato e aceita qualquer implementação de peça.

**Princípios:** DIP, OCP e baixo acoplamento.

### `Jogador`

Recebeu a cor controlada e o método:

```java
controla(Peca peca)
```

Esse método substitui a comparação extensa entre turno e quatro códigos de peça. O próprio jogador possui a informação necessária para verificar a posse.

**Princípio:** GRASP Especialista na Informação.

### `FormatadorJogo` — nova classe GUI

Recebeu o conteúdo que antes estava em `Jogo.toString()`:

```java
formatar(Jogo jogo)
```

Na versão original, `JanelaPrincipal` chamava:

```java
jogo.toString()
```

para exibir o menu **Status**. Agora a GUI chama:

```java
formatadorJogo.formatar(jogo)
```

A regra da partida deixou de conhecer textos de apresentação.

**Princípios:** SRP e separação entre domínio e interface.

### `JanelaPrincipal`

- Continua recebendo os cliques do jogador.
- Continua chamando `Jogo.moverPeca(...)`.
- Passou a usar `FormatadorJogo` no menu **Status**.
- O código gerado para a montagem da interface Swing foi preservado.

### `TabuleiroGUI`

- Continua atualizando a representação visual do tabuleiro.
- Passou a consultar `CorPeca` e `TipoPeca` em vez dos quatro códigos numéricos.

### `CasaGUI`

Continua responsável pelos ícones, destaque e aparência de cada casa. As constantes de imagens foram mantidas por representarem recursos visuais imutáveis, e não códigos de comportamento.

### `App`

Permaneceu sem alterações relevantes. Sua responsabilidade continua sendo iniciar a interface gráfica.

## Resultado

A refatoração preserva as regras e o funcionamento do jogo, mas redistribui responsabilidades:

- coordenação da partida: `Jogo`;
- validação e capturas: `ValidadorMovimento`;
- casas, limites, posição inicial e promoção: `Tabuleiro`;
- comportamento comum: `PecaAbstrata`;
- comportamentos específicos: `Pedra` e `Dama`;
- posse da peça: `Jogador`;
- apresentação do status: `FormatadorJogo`;
- representação visual: classes GUI.

O objetivo não foi criar novas funcionalidades, mas tornar o código mais coeso, legível e organizado.
