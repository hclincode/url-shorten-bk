#!/bin/bash

dir="$(dirname $0)/.."
cd $dir
gradle bootRun || echo "=========If found errors, 1. have database available? 2. update application.properties?========="
