.data
y:	.byte		37
z:	.byte		0
x:	.byte		13
	
.text
#loading the address and values 
la $t0, y
lb $t1, 0($t0)
lb $t2, 1($t0)
lb $t3, 2($t0)

#operation and store the result
sub  $t2, $t1, $t3
sb $t2, 1($t0)

#overwrite y and x
sb $t2, 2($t0)
sb $t2, 0($t0)

#termination
li $v0, 10
syscall
