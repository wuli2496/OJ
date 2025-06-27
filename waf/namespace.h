#ifndef NAMESPACE_H
#define NAMESPACE_H

#define BEGIN_NAMESPACE(ns) namespace ns {

#define END_NAMESPACE }

#ifdef WIN32
    #ifdef LIBRARY_EXPORTS
    #define LIBRARY_API __declspec(dllexport)
    #else
    #define LIBRARY_API __declspec(dllimport)
    #endif
#else
    #define LIBRARY_API
#endif

#endif // NAMESPACE_H
