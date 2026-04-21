import java.util.List;
import static java.util.Collections.indexOfSubList;

class RelationshipComputer<E> {
	public Relationship computeRelationship(List<E> left, List<E> right) {
		if (left.equals(right))
			return Relationship.EQUAL;

		if (indexOfSubList(right, left) >= 0)
			return Relationship.SUBLIST;

		if (indexOfSubList(left, right) >= 0)
			return Relationship.SUPERLIST;

		return Relationship.UNEQUAL;
	}
}