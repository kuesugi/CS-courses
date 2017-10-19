.data
Array_A:	.word 0xa1a2a3a4, 0xa5a6a7a8
msg1: 		.asciiz "Please enter element type (‘w’-word, ‘h’-half, ‘b’-byte): \n"
msg2: 		.asciiz "\nHere is the output (address, value in hexadecimal, value in decimal): \n"
msg3:		.asciiz ", "
msg4:		.asciiz "\n"

.text
#print the prompt
li $v0, 4
la $a0, msg1
syscall
#get the input
li $v0, 12
syscall
move $t0, $v0
#compare
la $t1, Array_A

beq $t0, 0x77, lableW
beq $t0, 0x62, lableB
beq $t0, 0x68, lableH

j exit
#LABLE WORD
lableW:
	li $v0, 4
	la $a0, msg2
	syscall
	loopw:
	beq $t1, 0x10010008, exit 
	lw $t0, 0($t1)

	#address
	li $v0, 34
	move $a0, $t1
	syscall
	#, 
	li $v0, 4
	la $a0, msg3
	syscall
	#hex
	li $v0, 34
	move $a0, $t0
	syscall
	#, 
	li $v0, 4
	la $a0, msg3
	syscall
	
	#decimal
	li $v0, 36
	move $a0, $t0
	syscall
	
	#\n
	li $v0, 4
	la $a0, msg4
	syscall
	
	addi $t1, $t1, 4 
j loopw

#LABLE BYTE
lableB:
	li $v0, 4
	la $a0, msg2
	syscall
	loopb:
	beq $t1, 0x10010008, exit
	lbu $t0, 0($t1)

	#address
	li $v0, 34
	move $a0, $t1
	syscall
	#, 
	li $v0, 4
	la $a0, msg3
	syscall
	#hex
	li $v0, 34
	move $a0, $t0
	syscall
	
	#, 
	li $v0, 4
	la $a0, msg3
	syscall
	
	#decimal
	li $v0, 36
	move $a0, $t0
	syscall
	
	#\n
	li $v0, 4
	la $a0, msg4
	syscall
	
	addi $t1, $t1, 1 
	j loopb
	
#LABLE H-WORD
lableH:
	li $v0, 4
	la $a0, msg2
	syscall
	looph:
	beq $t1, 0x10010008, exit
	lhu $t0, 0($t1)
	
	#address
	li $v0, 34
	move $a0, $t1
	syscall
	#, and space
	li $v0, 4
	la $a0, msg3
	syscall
	#hex
	li $v0, 34
	move $a0, $t0
	syscall
	
	#, 
	li $v0, 4
	la $a0, msg3
	syscall
	
	#decimal
	li $v0, 36
	move $a0, $t0
	syscall
	
	#\n
	li $v0, 4
	la $a0, msg4
	syscall
	
	addi $t1, $t1, 2 
	j looph
	
#termination
exit:
	li $v0, 10
	syscall