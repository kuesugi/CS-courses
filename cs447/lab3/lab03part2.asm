.data
msg1: .asciiz "Please enter your integer: \n"
msg2: .asciiz "Here is the input in binary: "
msg3: .asciiz "\nHere is the input in hexadecimal: "
msg4: .asciiz "\nHere is the output in binary: "
msg5: .asciiz "\nHere is the output in hexadecimal: "

.text
#print the prompt
li $v0, 4
la $a0, msg1
syscall
#get the input
li $v0, 5
syscall
move $t0, $v0
#print the result in binary
li $v0, 4
la $a0, msg2
syscall

li $v0, 35
move $a0, $t0
syscall
#print the result in hex
li $v0, 4
la $a0, msg3
syscall

li $v0, 34
move $a0, $t0
syscall
#print the output in binary
li $v0, 4
la $a0, msg4
syscall

andi $t0, $t0, 0x000000f0
srl $t0, $t0, 4          # 4-bits 
move $a0, $t0  
li $v0, 35
syscall
#print the output in hex
li $v0, 4
la $a0, msg5
syscall

li $v0, 34
move $a0, $t0
syscall
#termination
li $v0, 10
syscall