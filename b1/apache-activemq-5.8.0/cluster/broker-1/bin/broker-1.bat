@echo off
set ACTIVEMQ_HOME="/home/ubuntu/apache-activemq-5.8.0"
set ACTIVEMQ_BASE="/home/ubuntu/apache-activemq-5.8.0/cluster/broker-1"

set PARAM=%1
:getParam
shift
if "%1"=="" goto end
set PARAM=%PARAM% %1
goto getParam
:end

%ACTIVEMQ_HOME%/bin/activemq %PARAM%