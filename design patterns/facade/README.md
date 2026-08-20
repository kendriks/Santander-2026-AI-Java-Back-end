# Facade em Java

O **Facade (Fachada)** é um padrão de projeto estrutural que fornece uma **interface simplificada para um conjunto de classes ou funcionalidades complexas**.

A ideia é esconder a complexidade interna de um sistema e oferecer ao cliente uma forma mais simples de utilizá-lo.

## 🎯 Quando usar?

O Facade é útil quando:

* Um sistema possui muitas classes e dependências.
* Uma operação exige várias etapas para ser executada.
* Você quer simplificar a utilização de uma funcionalidade.
* Deseja reduzir o acoplamento entre o cliente e as classes internas.

## 💻 Exemplo

Imagine um sistema de compra que precisa executar várias etapas:

```java
public class Estoque {

    public boolean verificarProduto(String produto) {
        System.out.println("Verificando estoque...");
        return true;
    }
}
```

```java
public class Pagamento {

    public void processar(double valor) {
        System.out.println("Processando pagamento de R$" + valor);
    }
}
```

```java
public class Envio {

    public void enviar(String produto) {
        System.out.println("Preparando envio de " + produto);
    }
}
```

Sem o Facade, o cliente precisaria conhecer todas essas classes:

```java
Estoque estoque = new Estoque();
Pagamento pagamento = new Pagamento();
Envio envio = new Envio();

if (estoque.verificarProduto("Notebook")) {
    pagamento.processar(3000);
    envio.enviar("Notebook");
}
```

### Criando a Facade

Podemos centralizar essa complexidade em uma única classe:

```java
public class CompraFacade {

    private final Estoque estoque;
    private final Pagamento pagamento;
    private final Envio envio;

    public CompraFacade() {
        this.estoque = new Estoque();
        this.pagamento = new Pagamento();
        this.envio = new Envio();
    }

    public void realizarCompra(String produto, double valor) {

        if (!estoque.verificarProduto(produto)) {
            System.out.println("Produto indisponível.");
            return;
        }

        pagamento.processar(valor);
        envio.enviar(produto);

        System.out.println("Compra realizada com sucesso!");
    }
}
```

Agora o cliente precisa conhecer apenas a Facade:

```java
CompraFacade compra = new CompraFacade();

compra.realizarCompra("Notebook", 3000);
```

## 🔑 Principais características

* Simplifica o acesso a sistemas complexos.
* Esconde detalhes de implementação.
* Reduz o acoplamento entre o cliente e os subsistemas.
* Centraliza operações que envolvem várias etapas.
* Não impede que as classes internas sejam acessadas diretamente quando necessário.

## 📊 Estrutura

```text
              Cliente
                 │
                 ↓
           CompraFacade
                 │
       ┌─────────┼─────────┐
       ↓         ↓         ↓
    Estoque   Pagamento   Envio
```

O cliente interage principalmente com a **Facade**, enquanto ela coordena as operações das classes internas.

## ⚠️ Atenção

O Facade não elimina nem substitui as classes do sistema. Ele apenas fornece uma **interface simplificada** para utilizá-las.

Também é importante evitar transformar a Facade em uma classe com responsabilidades excessivas.
