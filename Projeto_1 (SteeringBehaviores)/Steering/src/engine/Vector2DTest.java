package engine;
import engine.Vector2D;

public class Vector2DTest {

    public static void main(String[] args) {

        // Criando dois vetores para testar
        Vector2D v1 = new Vector2D(3, 4);
        Vector2D v2 = new Vector2D(1, 2);

        // Testando a função de subtração
        Vector2D resultSubtraction = v1.subtração(v2);
        System.out.println("Subtração (v1 - v2): (" + resultSubtraction.x + ", " + resultSubtraction.y + ")");

        // Testando a função de adição
        Vector2D resultAddition = v1.adicao(v2);
        System.out.println("Adição (v1 + v2): (" + resultAddition.x + ", " + resultAddition.y + ")");

        // Testando a multiplicação por um fator
        Vector2D resultTimes = v1.times(2);
        System.out.println("Multiplicação (v1 * 2): (" + resultTimes.x + ", " + resultTimes.y + ")");

        // Testando o produto interno
        double resultInnerProduct = v1.inner_product(v2);
        System.out.println("Produto Interno (v1 • v2): " + resultInnerProduct);

        // Testando a norma (magnitude)
        double resultNorma = v1.norma();
        System.out.println("Norma (||v1||): " + resultNorma);

        // Testando a distância entre v1 e v2
        double resultDistance = v1.distanceTo(v2);
        System.out.println("Distância (v1 para v2): " + resultDistance);

        // Testando a direção (vetor unitário)
        Vector2D resultDirection = v1.direction();
        System.out.println("Direção (vetor unitário de v1): (" + resultDirection.x + ", " + resultDirection.y + ")");

        // Testando a normalização
        Vector2D resultNormalize = v1.normalize();
        System.out.println("Normalização (v1 normalizado): (" + resultNormalize.x + ", " + resultNormalize.y + ")");
    }
}