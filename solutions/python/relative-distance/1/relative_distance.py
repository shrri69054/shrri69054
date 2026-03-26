"""Relative Distance."""
import collections


class RelativeDistance:
    """Relative Distance."""

    def __init__(self, family_tree: dict[str, list[str]]):
        relatives = collections.defaultdict(set)
        for a, bs in family_tree.items():
            # a is related to all people in bs
            relatives[a].update(bs)
            # everyone in bs is related to a,
            # as well as to everyone in bs that is not themselves
            for b in bs:
                relatives[b].add(a)
                relatives[b].update(c for c in bs if c != b)
        self.relatives = relatives

    def degree_of_separation(self, person_a: str, person_b: str) -> int:
        """Return the degree of separation between two people."""
        for person, label in [(person_a, "A"), (person_b, "B")]:
            if person not in self.relatives:
                raise ValueError(f"Person {label} not in family tree.")
        q = collections.deque([(0, person_a)])
        seen = set()
        while q:
            steps, person = q.popleft()
            if person == person_b:
                return steps
            for relative in self.relatives[person]:
                if relative not in seen:
                    q.append((steps + 1, relative))
                    seen.add(relative)
        raise ValueError("No connection between person A and person B.")
