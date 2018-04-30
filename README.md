# SpaceKey Implementation
> The Java implementation of several spacetial keyword querying algorithm implementations in SpaceKey Demo. 


## Usage
For this project, a concluding class called Methods is developed for easy usage of these implementations. All the functions needed to make use of any algorithm are specified in MethodsInteface.java and implemented in Methods.java in the 'main' package. 

* To use the algorithms, simply import the Methods class and call the functions inside. There is the description of functions in the class below.
* To see the detailed implementation, look into the 'mck', 'minsk', 'coskq' packages, which contain the implementation of multiple mCK, CoSKQ, minSK algorithms including their supporting datastructures.

#### Further Description of Usage
##### Methods.constructData(String locPath, String docPath)
This function constructs all the data structures for all the based on the data. Call it before you use any of the algorithms below. The UK dataset is included in the project folder too. 
locPath: Full path of the loc(location) file of the dataset; 
docPath: Full path of the doc(keywords) file of the dataset;

##### mCK implementaions
mCK will minimize the maximum pairwise distance in the returned set of locations, while they cover all the keywords.
Three algorithms are as follows, according to the original paper. Among them, performance: GKG < SKECaplus < Exact; Speed: GKG > SKECaplus > Exact. In the demo we use SKECaplus because it has quite good performance to speed ratio.
```
HashSet<Point> Methods.mckGKG(HashSet<String> keywords);
HashSet<Point> Methods.mckSKECaplus(HashSet<String> keywords);
HashSet<Point> Methods.mckExact(HashSet<String> keywords);
```
##### minSK implementations
minSK will minimize cost (|S|-1) * maxDist while the location set cover all keywords, where |S| is the size of the output set of locations, maxDist is maximum pairwise distance. 
Only one algorithms is implemented here because there are no trade-offs between algorithms within the original paper.
```
public HashSet<Point> minskScaleLune(HashSet<String> keywords);
```
##### CoSKQ implementations
CoSKQ will take a position as input, and minimize the specified cost function. There is 3 types of cost functions we could consider.
* Type 1 cost = sum(dist(obj, query)) - the sum of distance between each location in the returned location set and the queried position;
* Type 2 cost = maxDist(obj, query)) + maxDist(obj1, obj2) - maximum distance between the queried position and any point in the returned location set + maximum pariwise distance in the returned location set;
* Type 3 cost = max(maxDist(obj, query), maxDist(obj1, obj2)) - similar to Type 2 cost, but take the bigger one between the two instead of adding them together

All types are implemented as is in the original paper, and each type has one approximation algorithm and one exact algorithm. Approximation algorithm does not guarantee the optimal cost, but is very fast; Exact algorithm does gurantee the optimal cost, but is quite slower.
```
public HashSet<Point> coskqType1Appro(double query_x, double query_y, HashSet<String> keywords);
public HashSet<Point> coskqType1Exact(double query_x, double query_y, HashSet<String> keywords);
public HashSet<Point> coskqType2Appro(double query_x, double query_y, HashSet<String> keywords);
public HashSet<Point> coskqType2Exact(double query_x, double query_y, HashSet<String> keywords);
public HashSet<Point> coskqType3Appro(double query_x, double query_y, HashSet<String> keywords);
public HashSet<Point> coskqType3Exact(double query_x, double query_y, HashSet<String> keywords);
```
## Meta
Wang Jikun [@WangJikun](https://twitter.com/dbader_org) wagjk@hku.com
[https://github.com/WagJK/SpaceKey-Implementations]
