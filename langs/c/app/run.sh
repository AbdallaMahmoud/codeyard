# compile the c++ src file
gcc Solution.c -o Solution > output.txt 2>>error.txt

# remember the exit status
export gcc_result=$?

# write exit status in the status output file
echo $gcc_result >> status.txt

# if compiled successfully, go run stage
if [ "$gcc_result" = "0" ]; then
    # run with 4 seconds timeout, writing output or error to output file
    timeout -k 1s 4s ./Solution < input.txt > output.txt 2>>error.txt
    # append exit status to the status output file
    echo $? >> status.txt
fi
