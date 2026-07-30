#!/bin/bash

echo "停止8080端口服务..."

lsof -ti:8080 | xargs kill -9 2>/dev/null || true


echo "删除target目录..."

rm -rf target


echo "删除macOS ._* 文件..."

find . -name "._*" -type f -delete 2>/dev/null


echo "开始Maven编译..."

mvn clean compile -DskipTests


echo "完成!"