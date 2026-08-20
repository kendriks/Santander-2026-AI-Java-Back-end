# Strategy em Java

O **Strategy** é um padrão de projeto comportamental que permite **definir diferentes algoritmos ou comportamentos e torná-los intercambiáveis**.

A ideia principal é separar cada comportamento em uma classe própria, permitindo trocar a estratégia sem alterar a classe principal.

## 🎯 Quando usar?

O Strategy é útil quando:

* Existem diferentes formas de realizar uma mesma operação.
* Você precisa trocar um comportamento em tempo de execução.
* Quer evitar muitos `if/else` ou `switch`.
* Deseja facilitar a manutenção e a extensão do código.

## 💻 Exemplo

Primeiro, criamos uma interface para representar a estratégia:

```java
public interface EstrategiaPagamento {

    void pagar(double valor);
}
```

Depois, criamos diferentes implementações:

```java
public class PagamentoPix implements EstrategiaPagamento {

    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento de R$" + valor + " via PIX");
    }
}
```

```java
public class PagamentoCartao implements EstrategiaPagamento {

    @Override
    public void pagar(double valor) {
        System.out.println("Pagamento de R$" + valor + " via cartão");
    }
}
```

A classe que utiliza a estratégia:

```java
public class Checkout {

    private EstrategiaPagamento estrategia;

    public Checkout(EstrategiaPagamento estrategia) {
        this.estrategia = estrategia;
    }

    public void finalizarPagamento(double valor) {
        estrategia.pagar(valor);
    }
}
```

Uso:

```java
EstrategiaPagamento pix = new PagamentoPix();

Checkout checkout = new Checkout(pix);

checkout.finalizarPagamento(100);
```

Podemos trocar a estratégia sem modificar o `Checkout`:

```java
EstrategiaPagamento cartao = new PagamentoCartao();

Checkout checkout = new Checkout(cartao);

checkout.finalizarPagamento(100);
```

## 🔑 Principais características

* Define uma interface comum para diferentes comportamentos.
* Cada estratégia possui sua própria implementação.
* O comportamento pode ser alterado sem modificar a classe que o utiliza.
* Reduz a necessidade de estruturas condicionais complexas.
* Facilita a adição de novas estratégias.

## 📊 Estrutura

```text
             EstrategiaPagamento
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
   PagamentoPix       PagamentoCartao
          │                   │
          └─────────┬─────────┘
                    ↓
                 Checkout
```

## ⚠️ Atenção

O Strategy pode aumentar a quantidade de classes do projeto, já que cada comportamento normalmente possui sua própria implementação. Por isso, é interessante utilizá-lo quando existem **variações reais de comportamento** que precisam ser isoladas.
