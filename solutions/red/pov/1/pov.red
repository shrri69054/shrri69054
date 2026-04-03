Red [
	description: {"POV" exercise solution for exercism platform}
	author: "LucasSemS eu mrm" 
]

find-first: function [
  pred [function!]
  list [series!]
] [
  case [
    empty? list [ none ]
    pred (first list) [ first list ]
    true [ find-first :pred at list 2 ]
  ]
]

filter: function [ pred [function!] list [series!] ]
  [ collect [ foreach x list [ if pred x [ keep x ] ] ] ]

build-node: function [
  label
  children
] [
  node: make map! []
  node/label: label
  if not empty? children [
    node/children: children
  ]
  node
]

push-child: function [
  node
  new-child
] [
  either none? node/children [
    node/children: reduce [ new-child ]
  ] [
    append node/children new-child
  ]
  node
]

find-path: function [
    targ
    path
    left
] [
  either empty? left [
    none
  ] [
    found-path: seek targ path first left
    either none? found-path [
      find-path targ path at left 2
    ] [
      found-path
    ]
  ]
]

seek: function [
  targ
  path
  node
] [
  case [
    node/label = targ [ append path node/label ]
    or~ (empty? node/children) (none = node/children) [ none ]
    true [ find-path targ (append (copy path) node/label) node/children ]
  ]
]

find-node: function [ node-name node-list ]
  [ 
    id-fn: function [ n ] [ n/label = node-name ]
    find-first :id-fn node-list ]

remove-node: function [ node-name node-list ]
  [ filter function [ node ] [ node/label <> node-name ] copy node-list ]

rebuild: function [
  accum
  path
] [

  if empty? path [ return accum ]

  haystack: remove-node first path accum/children
  needle: find-node first path accum/children

  new-haystack: build-node copy accum/label haystack

  rebuild push-child needle new-haystack at path 2
]

path-rebuild: function [
  last-common
  from-left
  to-left
] [
  case [
    (first from-left) = (first to-left) [
      path-rebuild (first from-left) at from-left 2 at to-left 2
    ]
    last-common = none [
      reduce [ first from-left first to-left ]
    ]
    true [
      append append reverse (copy from-left) last-common (copy to-left)
    ]
  ]
]

from-pov: function [
	tree
	from
] [
  path: seek from [] tree
  either path <> none [
    rebuild tree at path 2
  ] [
    none
  ]
]

path-to: function [
	from
	to
	tree
] [
	from-path: seek from [] tree
  to-path: seek to [] tree 

  either and~ (from-path <> none) (to-path <> none) [
    path-rebuild none from-path to-path
  ] [
    none
  ]
]
