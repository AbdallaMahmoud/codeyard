# compile the c++ src file
mcs Solution.cs > output.txt 2>>error.txt

# remember the exit status
export mono_result=$?

# write exit status in the status output file
echo $mono_result >> status.txt

# if compiled successfully, go run stage
if [ "$mono_result" = "0" ]; then
    # run with 4 seconds timeout, writing output or error to output file
    timeout -k 1s 4s mono Solution.exe < input.txt > output.txt 2>>error.txt
    # append exit status to the status output file
    echo $? >> status.txt
fi
