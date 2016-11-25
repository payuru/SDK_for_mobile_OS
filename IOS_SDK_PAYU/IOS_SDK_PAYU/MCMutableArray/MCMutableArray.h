//
//  MCMutableArray.h
//  termocos.ru
//
//  Created by Max on 03.09.14.
//  Copyright (c) 2014 IPOL OOO. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MCMutableArray : NSMutableArray{
    NSMutableArray *_backingStore;
    NSMutableDictionary *_dicKey;

}
-(void)addObject:(NSString*)value key:(NSString*)key;
-(void)addHash:(NSString*)obj;
-(NSString*)postString;
@end
