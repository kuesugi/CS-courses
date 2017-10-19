.data
msg1: .asciiz "What is the first value? \n"
msg2: .asciiz "What is the second value? \n"
msg3: .asciiz "The sum of "
msg4: .asciiz " and "
msg5: .asciiz " is "

.text
#print the first prompt
li $v0, 4
la $a0, msg1
syscall
#get the input
li $v0, 5
syscall
move $t0, $v0
#print the second prompt
li $v0, 4
la $a0, msg2
syscall
#get the input
li $v0, 5
syscall
move $t1, $v0
#print "the result of"
li $v0, 4
la $a0, msg3
syscall
#print the first value
li $v0, 1
move $a0, $t0
syscall
#print "and"
li $v0, 4
la $a0, msg4
syscall
#print the second value
li $v0, 1
move $a0, $t1
syscall
#print "is"
li $v0, 4
la $a0, msg5
syscall
#operate and print
add $t1, $t1, $t0
li $v0, 1
move $a0, $t1
syscall
#termination
li $v0, 10
syscall









