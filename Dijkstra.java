import java.util.Scanner;

public class Dijkstra {

    public static void main(String[] args) {

        String[] pontos = {
            "Rodoviária",
            "Super Fácil",
            "Rjr Gesso",
            "Mercadinho Popular",
            "Contrukasa",
            "Caixa Econômica",
            "Arena das Dunas"
        };

        // Matriz de distâncias em metros.
        // O grafo é não direcionado.
        // Zero indica que não existe ligação direta.

        int[][] grafo = {
            // Rod  SF  RJ  MP  CK  CE  AD
            {   0, 630,   0,    0,    0,    0,    0}, // Rodoviária
            { 630,   0, 635,    0,    0, 2600,    0}, // Super Fácil
            {   0, 635,   0,  500,    0,    0,    0}, // Rjr Gesso
            {   0,   0, 500,    0, 2100,    0,    0}, // Mercadinho Popular
            {   0,   0,   0, 2100,    0,  800, 2100}, // Contrukasa
            {   0, 2600,   0,    0,  800,    0, 2050}, // Caixa Econômica
            {   0,   0,   0,    0, 2100, 2050,    0}  // Arena das Dunas
        };

        int origem;
        int destino;

        try (Scanner entrada = new Scanner(System.in)) {

            origem = lerPonto(
                entrada, pontos, "Digite o ponto de origem: "
            );

            if (origem == -1) {
                return;
            }

            destino = lerPonto(
                entrada, pontos, "Digite o ponto de destino: "
            );

            if (destino == -1) {
                return;
            }
        }

        int[] distancia = new int[pontos.length];
        boolean[] visitado = new boolean[pontos.length];
        String[] rota = new String[pontos.length];

        // Inicializa todas as distâncias como infinito.
        for (int i = 0; i < pontos.length; i++) {
            distancia[i] = Integer.MAX_VALUE;
        }

        // A distância da origem até ela mesma é zero.
        distancia[origem] = 0;
        rota[origem] = pontos[origem];

        // Algoritmo de Dijkstra.
        while (true) {

            int atual = -1;
            int menor = Integer.MAX_VALUE;

            // Procura o ponto não visitado com menor distância.
            for (int i = 0; i < pontos.length; i++) {

                if (!visitado[i] && distancia[i] < menor) {
                    menor = distancia[i];
                    atual = i;
                }
            }

            // Para se não houver mais pontos alcançáveis
            // ou se o destino já tiver sido encontrado.
            if (atual == -1 || atual == destino) {
                break;
            }

            visitado[atual] = true;

            // Verifica os vizinhos do ponto atual.
            for (int vizinho = 0; vizinho < pontos.length; vizinho++) {

                int peso = grafo[atual][vizinho];

                if (peso > 0 &&
                    menor + peso < distancia[vizinho]) {

                    distancia[vizinho] = menor + peso;

                    rota[vizinho] =
                        rota[atual] + " -> " + pontos[vizinho];
                }
            }
        }

        // Verifica se existe uma rota.
        if (rota[destino] == null) {
            System.out.println("Não existe rota entre os dois pontos.");
            return;
        }

        // Exibe o resultado.
        System.out.println("\nMelhor rota: " + rota[destino]);
        System.out.println("Distância: " + distancia[destino] + " m");
    }

    // Recebe o nome do ponto digitado pelo usuário.
    static int lerPonto(
        Scanner entrada,
        String[] pontos,
        String pergunta
    ) {

        System.out.println("\nPontos disponíveis:");

        for (String ponto : pontos) {
            System.out.println("- " + ponto);
        }

        while (true) {

            System.out.print(pergunta);

            if (!entrada.hasNextLine()) {
                return -1;
            }

            String nome = entrada.nextLine().trim();

            for (int i = 0; i < pontos.length; i++) {

                if (pontos[i].equalsIgnoreCase(nome)) {
                    return i;
                }
            }

            System.out.println(
                "Ponto inválido. Digite um dos nomes apresentados."
            );
        }
    }
}
