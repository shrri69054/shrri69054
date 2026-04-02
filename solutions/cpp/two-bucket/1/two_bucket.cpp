#include "two_bucket.h"
#include <unordered_set>
#include <queue>
#include <stdexcept>

namespace two_bucket {

measure_result measure(int bucket1_capacity, int bucket2_capacity,
                       int target_volume, bucket_id start_bucket)
{
    measure_result result = { };

    struct State
    {
        int b1;
        int b2;

        bool operator==(const State &o) const { return b1 == o.b1 && b2 == o.b2; }

        struct Hash
        {
            size_t operator()(const State &s) const
            {
                size_t h = std::hash<int>{}(s.b1);
                h ^= std::hash<int>{}(s.b2) + 0x9e3779b9 + (h << 6) + (h >> 2);
                return h;
            }
        };
    };

    struct StateMoves : State
    {
        int moves;
    };

    auto forbiddenState = [&](const State &s) {
        return (start_bucket == bucket_id::one) ? (s.b1 == 0 && s.b2 == bucket2_capacity) : (s.b2 == 0 && s.b1 == bucket1_capacity);
    };

    std::unordered_set<State, State::Hash> visited;
    std::queue<StateMoves> q;

    StateMoves start = {};
    if (start_bucket == bucket_id::one)
        start.b1 = bucket1_capacity;
    else
        start.b2 = bucket2_capacity;

    q.push(start);
    visited.insert(start);

    while (!q.empty()) {
        auto cur = q.front();
        q.pop();

        if (cur.b1 == target_volume) {
            result.num_moves = cur.moves + 1;
            result.goal_bucket = bucket_id::one;
            result.other_bucket_volume = cur.b2;
            return result;
        }

        if (cur.b2 == target_volume) {
            result.num_moves = cur.moves + 1;
            result.goal_bucket = bucket_id::two;
            result.other_bucket_volume = cur.b1;
            return result;
        }

        auto tryNext = [&](StateMoves s) {
            if (!visited.count(s) && !forbiddenState(s)) {
                visited.insert(s);
                q.push(s);
            }
        };

        /* Fill bucket 1 */
        tryNext({ bucket1_capacity, cur.b2, cur.moves + 1 });
        /* Fill bucket 2 */
        tryNext({ cur.b1, bucket2_capacity, cur.moves + 1 });
        /* Empty bucket 1 */
        tryNext({ 0, cur.b2, cur.moves + 1 });
        /* Empty bucket 2 */
        tryNext({ cur.b1, 0, cur.moves + 1 });

        /* Pour b1 -> b2 */
        int pour = bucket2_capacity - cur.b2 < cur.b1 ? bucket2_capacity - cur.b2 : cur.b1;
        tryNext({ cur.b1 - pour, cur.b2 + pour, cur.moves + 1 });

        /* Pour b2 -> b1 */
        pour = bucket1_capacity - cur.b1 < cur.b2 ? bucket1_capacity - cur.b1 : cur.b2;
        tryNext({ cur.b1 + pour, cur.b2 - pour, cur.moves + 1 });
    }

    throw std::runtime_error("");
    return result; /* impossible */
}

}  // namespace two_bucket





