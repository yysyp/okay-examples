#! /bin/bash -eu

#docker run --rm -i \
#  --workdir /mnt \
#  -v $(pwd):/mnt \
#  busybox:1.32 \
#  -- sh -c "cat /tmp/text-input.txt; echo 'Container' > /tmp/text-output.output"
#  $@

docker run --rm -i --workdir //c/Users/Dell --volume //c/Users/Dell:/tmp busybox:1.32 sh -c "echo 'Container' >> /tmp/text-output.output; cat /tmp/text-output.output"
echo "find the result in //c/Users/Dell"

#Windows:
#docker run -it --rm --workdir /mnt --volume c:/Users/Dell/GitHub/poc:/mnt --volume C:/Users/Dell/.m2:/root/.m2 nexus.xxx.com:8888/a/b/c/maven bash -c "mvn package -Dmaven.test.skip=true"
