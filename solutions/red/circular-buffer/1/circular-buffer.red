Red [
	description: {"Circular Buffer" exercise solution for exercism platform}
	author: "LucasSemS eu mrm"
]

array: function [n] [
	arr: []
	while [n > 0] [
		append arr 'none
		n: n - 1
    ]
	:arr
]

run: function [
	capacity
	operations
] [
	buf: array capacity
	read-idx: 1
	write-idx: 1
	size: 0
	res: []

	while [(length? operations) > 0] [
		switch mold first operations [
			"read" [
				either size = 0 [
					append res 'false
                ][
					append res buf/(:read-idx)
   					read-idx: (read-idx % capacity) + 1
					size: size - 1
                ]
            ]
			"write" [
				either size = capacity [
					append res 'false
                ][
					buf/(:write-idx): second operations
					write-idx: (write-idx % capacity) + 1
					size: size + 1
					append res 'true
                ]
                operations: next operations
            ]
			"overwrite" [
				either size = capacity [
					buf/:write-idx: second operations
					write-idx: (write-idx % capacity) + 1
					read-idx: (read-idx % capacity) + 1
                ][
					buf/(:write-idx): second operations
					write-idx: (write-idx % capacity) + 1
					size: size + 1
                ]
                operations: next operations
            ]
			"clear" [
				size: 0
				read-idx: 1
				write-idx: 1
            ]
        ]
		operations: next operations
    ]
	res
]
