cmake_minimum_required(VERSION 3.4.1)

add_library(AppNativeData SHARED
        AppNativeData.c)

# Include libraries needed for AppNativeData lib
target_link_libraries(AppNativeData
                      android
                      log)