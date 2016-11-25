//
//  MCMutableArray.m
//  termocos.ru
//
//  Created by Max on 03.09.14.
//  Copyright (c) 2014 IPOL OOO. All rights reserved.
//

#import "MCMutableArray.h"

@implementation MCMutableArray
- (id) init
{
    self = [super init];
    if (self != nil) {
        _backingStore = [NSMutableArray new];
        _dicKey=[NSMutableDictionary new];
    }
    return self;
}

- (void) dealloc
{
    _backingStore = nil;
}

#pragma mark NSArray

-(NSUInteger)count
{
    return [_backingStore count];
}

-(id)objectAtIndex:(NSUInteger)index
{
    return [_backingStore objectAtIndex:index];
}

#pragma mark NSMutableArray

-(void)insertObject:(id)anObject atIndex:(NSUInteger)index
{
    [_backingStore insertObject:anObject atIndex:index];
}

-(void)removeObjectAtIndex:(NSUInteger)index
{
    [_backingStore removeObjectAtIndex:index];
}
-(void)addHash:(NSString*)obj{
    if(obj!=nil&&([obj isKindOfClass:[NSString class]]||[obj isKindOfClass:[NSMutableString class]])){
        [self addObject:[NSString stringWithFormat:@"%lu%@", [obj lengthOfBytesUsingEncoding:NSUTF8StringEncoding] , obj]];
    }
}
-(void)addObject:(NSString*)value key:(NSString*)key{
    if(key!=nil&&value!=nil){
        [self addObject:[NSString stringWithFormat:@"%@=%@", key, value]];
        [_dicKey setObject:[NSString stringWithFormat:@"%@=%@", key, value] forKey:key];
    }
}
-(NSString*)postString{
    NSMutableString *str=[[NSMutableString alloc] init];
    NSArray *sortedKeys = [[_dicKey allKeys] sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];
    for (NSString *key in sortedKeys) {
        NSString *encodedValue = [_dicKey objectForKey:key];
        [str appendFormat:@"%@&",encodedValue];
    }
    if(str.length>1)
        [str deleteCharactersInRange:NSMakeRange([str length]-1, 1)];
    return str;
}
-(void)addObject:(id)anObject
{
    [_backingStore addObject:anObject];
}

-(void)removeLastObject
{
    [_backingStore removeLastObject];
}

-(void)replaceObjectAtIndex:(NSUInteger)index withObject:(id)anObject
{
    [_backingStore replaceObjectAtIndex:index withObject:anObject];
}

@end
