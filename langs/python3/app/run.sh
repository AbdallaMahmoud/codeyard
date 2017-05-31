# run with 4 seconds timeout, writing output or error to output file
timeout -k 1s 4s python Solution.py < input.txt > output.txt 2>>error.txt
# append exit status to the status output file
echo $? > status.txt
