# WorleyNoise
Demonstrator of Worley noise.

This shows an implementation of Worley noise in Java with LibGDX. 

This implementation generates a 3d block of Worley noise and advances along the Z-axis over time to provide an animated effect.
To enhance performance the area is subdivided into cubes and only one feature point is generated per cube.  This speeds up the determination 
of the closest point for each pixel as only the 27 closest cubes need to be considered (3x3x3). 
This implementation only looks at the distance to the closest feature point as it seems the most interesting (other effects could be created by looking
at the second closest, or third closest feature point).

The slider settings allows to set the size of the cubes (i.e. to change the number of feature points).
The checkbox simply inverts the colours.
The last slider determines the scaling from a pixel's distance to the closest feature point to a shade of grey.


![WorleyScreenShot](https://user-images.githubusercontent.com/49096535/215554414-c4528954-f706-4604-847f-e5f06f45af2c.png)
