# retrieve dockerImage as the first argument
DOCKER_IMG="$1"

# create a volume
volume_id=$(docker volume create)

# create a container
cont_id=$(docker  create -i -t -v \
        $volume_id:/app \
        -w="/app" \
        -m 512m \
        --network="none" \
        $DOCKER_IMG bash ./run.sh)
        
# copy code and runner script from the host to the container
docker cp app/. $cont_id:/app

# start the container
docker start -a -i $cont_id

# copy the code output from the container to the host
docker cp $cont_id:/app/output.txt output.txt

# copy the error output from the container to the host
docker cp $cont_id:/app/error.txt error.txt


# copy the code exit status from the container to the host
docker cp $cont_id:/app/status.txt status.txt

# delete the container
docker rm $cont_id

# delete the volume
docker volume rm $volume_id
