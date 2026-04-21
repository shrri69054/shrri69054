#include <vector>
#include "rail_fence_cipher.h"

using namespace std;

static auto create_rails(size_t rail_count, size_t rail_length) -> vector<string>
{
  return vector<string>(rail_count, string(rail_length, '\0'));
}

static auto fill_rails_along_zig_zag(vector<string>& rails, const string_view text) -> void
{
  auto x = size_t{ 0 };
  auto y = size_t{ 0 };
  auto dy = int{ 1 };

  for(auto c : text) {
    rails[y][x++] = c;

    if(rails.size() > 1) {
      if((y == 0) && (dy == -1)) {
        dy = 1;
      }
      if((y + 1 == rails.size()) && (dy == 1)) {
        dy = -1;
      }
      y += dy;
    }
  }
}

static auto read_rails_by_rail(const vector<string>& rails) -> string
{
  auto read = string{};

  for(auto& rail : rails) {
    for(auto c : rail) {
      if(c) {
        read += c;
      }
    }
  }

  return read;
}

static auto overlay_onto_rails(vector<string>& rails, const string_view text) -> void
{
  auto offset = size_t{ 0 };

  for(auto& rail : rails) {
    for(auto& c : rail) {
      if(c) {
        c = text[offset++];
      }
    }
  }
}

static auto read_rails_along_zig_zag(const vector<string>& rails) -> string
{
  auto read = string{};

  auto x = size_t{ 0 };
  auto y = size_t{ 0 };
  auto dy = int{ 1 };

  for(auto i = size_t{ 0 }; i < rails[0].length(); i++) {
    read += rails[y][x++];

    if(rails.size() > 1) {
      if((y == 0) && (dy == -1)) {
        dy = 1;
      }
      if((y + 1 == rails.size()) && (dy == 1)) {
        dy = -1;
      }
      y += dy;
    }
  }

  return read;
}

auto rail_fence_cipher::encode(const string& plaintext, int rail_count) -> string
{
  auto rails = create_rails(rail_count, plaintext.length());
  fill_rails_along_zig_zag(rails, plaintext);
  return read_rails_by_rail(rails);
}

auto rail_fence_cipher::decode(const string& ciphertext, int rail_count) -> string
{
  auto rails = create_rails(rail_count, ciphertext.length());
  fill_rails_along_zig_zag(rails, ciphertext);
  overlay_onto_rails(rails, ciphertext);
  return read_rails_along_zig_zag(rails);
}





