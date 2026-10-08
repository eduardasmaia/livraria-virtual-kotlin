class Livro(val nome: String, val preco: Double, val genero: String)

class Cliente(val nome: String, val email: String)

class Pedido(
    val cliente: Cliente,
    val livros: List<Livro>,
    val cupomDesconto: Double? = null,
    var statusEntrega: String = "Pendente"
) {
    fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (livro in livros) {
            subtotal += livro.preco
        }
        return subtotal
    }

    fun calcularFrete(subtotal: Double): Double =
        if (subtotal >= 100.0) 0.0 else 15.0

    fun classificarPedido(): String {
        if (livros.isEmpty()) return "Carrinho Vazio"

        return when (livros.first().genero.lowercase()) {
            "tecnologia" -> "Pacote Tech & Programação"
            "ficção" -> "Pacote Aventura & Ficção"
            "romance" -> "Pacote Literatura & Romance"
            else -> "Pacote de Gêneros Variados"
        }
    }

    fun exibirResumo() {
        if (livros.isEmpty()) {
            println("O carrinho de ${cliente.nome} está vazio.\n")
            return
        }

        val subtotal = calcularSubtotal()
        val frete = calcularFrete(subtotal)
        val desconto = cupomDesconto ?: 0.0
        val totalFinal = (subtotal + frete) - desconto

        println("""
            ===================================
            📖 RESUMO DO PEDIDO
            ===================================
            Cliente: ${cliente.nome} (${cliente.email})
            -----------------------------------
            Itens no Carrinho:
        """.trimIndent())

        for (livro in livros) {
            println("- ${livro.nome} | ${livro.genero} | R$ %.2f".format(livro.preco))
        }

        println("""
            -----------------------------------
            Subtotal: R$ %.2f
            Frete: R$ %.2f
            Desconto: R$ %.2f
            TOTAL A PAGAR: R$ %.2f
            -----------------------------------
            Classificação: ${classificarPedido()}
            Status da Entrega: $statusEntrega
            ===================================
            
        """.trimIndent().format(subtotal, frete, desconto, totalFinal))
    }
}

fun main() {
    val cliente1 = Cliente("Ana Costa", "ana@email.com")
    val livrosCliente1 = listOf(
        Livro("Kotlin in Action", 120.0, "Tecnologia"),
        Livro("O Senhor dos Anéis", 85.0, "Ficção")
    )

    val pedido1 = Pedido(cliente1, livrosCliente1, statusEntrega = "Enviado")
    pedido1.exibirResumo()

    val cliente2 = Cliente("Carlos Silva", "carlos@email.com")
    val livrosCliente2 = listOf(Livro("1984", 45.0, "Ficção"))

    val pedido2 = Pedido(cliente2, livrosCliente2, cupomDesconto = 10.0, statusEntrega = "Processando")
    pedido2.exibirResumo()
}