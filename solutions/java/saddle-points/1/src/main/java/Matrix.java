import java.util.*;

class Matrix {

    final Set<MatrixCoordinate> saddlePoints = new HashSet<>();

    Matrix(List<List<Integer>> intLists) {
        for (int i = 0; i < intLists.size(); i++) {
            var row = intLists.get(i);
            List<Integer> maxIndexes = new ArrayList<>();
            var rowMax = Collections.max(row);

            for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                var rowValue = row.get(rowIndex);
                if (rowValue.equals(rowMax)) maxIndexes.add(rowIndex);
            }

            for (var rowIndex : maxIndexes) {
                List<Integer> column = new ArrayList<>();
                for (var rowX : intLists) column.add(rowX.get(rowIndex));
                Integer colMin = Collections.min(column);
                if (row.get(rowIndex).equals(colMin)) saddlePoints.add(new MatrixCoordinate(i + 1, rowIndex + 1));
            }
        }
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        return saddlePoints;
    }
}