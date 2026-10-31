import java.util.*;

public class Prism {
    // Допуск расстояния от точки до линии луча.
    // В тестах координаты квантуются шагом примерно 0.2, поэтому допускаем половину шага + маленький запас.
    private static final double MAX_DISTANCE_FROM_RAY = 0.11;
    // Отдельный маленький допуск для проверки "впереди луча", чтобы не брать текущую точку.
    private static final double FORWARD_EPS = 1e-9;

    public record LaserInfo(double x, double y, double angle) {
    }

    public record PrismInfo(int id, double x, double y, double angle) {
    }

    public static List<Integer> findSequence(LaserInfo laser, List<PrismInfo> prisms) {
        // Сюда собираем id призм в порядке попадания луча.
        List<Integer> answer = new ArrayList<>();

        // Текущее состояние луча: позиция и угол.
        double currentX = laser.x();
        double currentY = laser.y();
        double currentAngle = laser.angle();

        // Шагаем по траектории луча, пока впереди есть следующая призма.
        while (true) {
            // Приводим угол к [0, 360), затем переводим в радианы для cos/sin.
            double angle = normalizeAngle(currentAngle);
            double radians = Math.toRadians(angle);

            // Направляющий вектор луча d = (dx, dy): куда луч летит в текущий момент.
            double dx = Math.cos(radians);
            double dy = Math.sin(radians);

            // Ищем ближайшую призму впереди на текущем луче.
            PrismInfo nextPrism = null;
            double bestDistance = Double.POSITIVE_INFINITY;

            for (PrismInfo prism : prisms) {
                // Вектор v = (vx, vy) от текущей точки луча до призмы.
                double vx = prism.x() - currentX;
                double vy = prism.y() - currentY;

                // Векторное произведение (2D): d x v = dx*vy - dy*vx.
                // Если около нуля, призма лежит на той же прямой, что и луч.
                double cross = dx * vy - dy * vx;
                if (Math.abs(cross) > MAX_DISTANCE_FROM_RAY) {
                    continue;
                }

                // Скалярное произведение: d · v = dx*vx + dy*vy.
                // dot > 0 => призма впереди луча, dot < 0 => позади.
                double dot = dx * vx + dy * vy;
                if (dot <= FORWARD_EPS) {
                    continue;
                }

                // Среди подходящих берем минимальный dot: это ближайшая призма по ходу луча.
                if (dot < bestDistance) {
                    bestDistance = dot;
                    nextPrism = prism;
                }
            }

            // Впереди ничего нет: луч уходит в пустоту, завершаем.
            if (nextPrism == null) {
                break;
            }

            // Фиксируем попадание и обновляем состояние луча после преломления.
            answer.add(nextPrism.id());
            currentX = nextPrism.x();
            currentY = nextPrism.y();
            currentAngle += nextPrism.angle();
        }

        return answer;
    }

    private static double normalizeAngle(double angle) {
        // Нормализация к диапазону [0, 360).
        double normalized = angle % 360.0;
        if (normalized < 0) {
            normalized += 360.0;
        }

        return normalized;
    }
}
