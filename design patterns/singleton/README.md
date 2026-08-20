# Singleton em Java

O **Singleton** é um padrão de projeto criacional que garante que uma classe tenha **apenas uma instância** durante a execução da aplicação e fornece um ponto de acesso global a essa instância.

## 🎯 Quando usar?

O Singleton pode ser utilizado quando é necessário compartilhar uma única instância de determinado recurso, como:

* Configurações da aplicação
* Gerenciadores
* Serviços que devem possuir uma única instância
* Controle de recursos compartilhados

## 💻 Exemplo

```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

Uso:

```java
Singleton primeiro = Singleton.getInstance();
Singleton segundo = Singleton.getInstance();

System.out.println(primeiro == segundo); // true
```

Nesse exemplo, `primeiro` e `segundo` apontam para a **mesma instância**.

## 🔑 Principais características

* Construtor `private` para impedir a criação direta de objetos.
* Instância armazenada em uma variável `static`.
* Método `getInstance()` responsável por fornecer a instância.
* Garante uma única instância dentro do contexto definido pela implementação.

## ⚠️ Atenção

O Singleton deve ser utilizado com cuidado. O uso excessivo pode criar **estado global**, aumentar o acoplamento entre componentes e dificultar testes.

