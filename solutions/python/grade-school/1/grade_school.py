"""
Exercism solution for "grade-school"
"""

import sqlite3

CREATE_TABLE = "CREATE TABLE grades (grade integer, student text UNIQUE)"
ADD_STUDENT_TO_GRADE = "INSERT INTO grades VALUES (?, ?)"
GET_STUDENT_BY_GRADE = "SELECT student FROM grades WHERE grade=? ORDER BY student"
GET_STUDENTS_BY_GRADE = "SELECT student FROM grades ORDER BY grade, student"


class School:
    """
    Simple representation of a School database.
    """

    def __init__(self) -> None:
        self._db = sqlite3.connect(":memory:")
        self._db.row_factory = sqlite3.Row
        with self._db:
            self._db.execute(CREATE_TABLE)
        self._added: list[bool] = []

    def added(self) -> list[bool]:
        """
        Get the success / failure of recent additions.
        """
        return self._added

    def add_student(self, name: str, grade: int) -> None:
        """
        Add the student to the database.
        """
        with self._db:
            try:
                self._db.execute(ADD_STUDENT_TO_GRADE, (grade, name))
            except sqlite3.IntegrityError:
                self._added.append(False)
            else:
                self._added.append(True)

    def grade(self, grade: int) -> list[str]:
        """
        Return the list of students in the grade.
        """
        query = self._db.execute(GET_STUDENT_BY_GRADE, (grade,))
        return [row["student"] for row in query]

    def roster(self) -> list[str]:
        """
        Return the roster of students per grade.
        """
        query = self._db.execute(GET_STUDENTS_BY_GRADE)
        return [row["student"] for row in query]

    def __del__(self):
        self._db.close()