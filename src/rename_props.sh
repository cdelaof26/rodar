#!/bin/bash

directories=("pmmicro")

for dir in "${directories[@]}"; do
    if [ -d "$dir" ]; then
        source_file="$dir/src/main/resources/application-template.properties"
        dest_file="$dir/src/main/resources/application.properties"
        
        if [ -f "$source_file" ]; then
            mv "$source_file" "$dest_file"
        fi
    fi
done
