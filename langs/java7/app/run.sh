# compile the java src file
javac Solution.java > output.txt 2>>error.txt

# remember the exit status
export java_result=$?

# write exit status in the status output file
echo $java_result >> status.txt

# if compiled successfully, go run stage
if [ "$java_result" = "0" ]; then
    # run with 4 seconds timeout, writing output or error to output file
    timeout -k 1s 4s java Solution < input.txt > output.txt 2>>error.txt
    # append exit status to the status output file
    echo $? >> status.txt
fi
