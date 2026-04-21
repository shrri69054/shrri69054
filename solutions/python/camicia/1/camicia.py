import collections

PENALTY = {"A": 4, "K": 3, "Q": 2, "J": 1}


class Game:

    def __init__(self, cards: list[list[str]]):
        cards = [["-" if i.isdecimal() else i for i in v] for v in cards]
        self.hands = {a: collections.deque(reversed(b)) for a, b in zip([True, False], cards)}
        self.middle: list[str] = []
        self.seen: set[tuple[tuple[str, ...], ...]] = set()
        self.cur_player = True
        self.cards = 0
        self.tricks = 0
        self.loop = False

    def game_over(self) -> bool:
        """Return if the game should stop.

        The game stops if one player has all the cards of if we are in a loop.
        """
        if not all(self.hands.values()) and not self.middle:
            return True

        # Check for a loop.
        state = tuple(tuple(i) for i in self.hands.values())
        self.loop = state in self.seen
        self.seen.add(state)
        return self.loop

    def end_turn(self) -> None:
        """End the player's turn."""
        self.cur_player = not self.cur_player

    def collect_middle(self) -> None:
        """Add the middle pile to the player's hand."""
        self.hands[self.cur_player].extendleft(self.middle)
        self.tricks += 1
        self.middle.clear()

    def play_one_card(self) -> str:
        """Move one card from the current player's hand to the middle."""
        card = self.hands[self.cur_player].pop()
        self.middle.append(card)
        self.cards += 1
        return card

    def resolve_penalty(self, penalty: int) -> None:
        """Play out a penalty."""
        while penalty and self.hands[self.cur_player]:
            card = self.play_one_card()
            penalty -= 1
            if card != "-":
                self.end_turn()
                penalty = PENALTY[card]

        # Collect the pile.
        self.end_turn()
        self.collect_middle()

    def play(self) -> dict[str, str | int]:
        """Return the outcome of the game."""
        while not self.game_over():
            if not self.hands[self.cur_player]:
                self.end_turn()
                self.collect_middle()
                break

            card = self.play_one_card()
            self.end_turn()
            if card != "-":
                self.resolve_penalty(PENALTY[card])

        return {
            "status": "loop" if self.loop else "finished",
            "cards": self.cards,
            "tricks": self.tricks,
        }


def simulate_game(player_a: list[str], player_b: list[str]) -> dict[str, str | int]:
    return Game([player_a, player_b]).play()
