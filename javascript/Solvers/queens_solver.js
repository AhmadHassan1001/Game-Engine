let queens_program=`
:- use_module(library(lists)).
is_element(e).
is_element(q).
is_queen(q).
isnot_queen(e).

is_list(Ls,N):- (N =:=1,nth0(0,Ls, E),is_element(E)   );
    (N >1, nth0(0,Ls, E, R),is_element(E),is_list(R,N-1)  ).
is_map(Mat,N):-(N =:=1,nth0(0,Mat, E),is_list(E,8)   );
    (N >1, nth0(0,Mat, E, R),is_list(E,8),is_map(R,N-1)  ).
is_queen_map(Mat):- is_map(Mat,8).
is_nth0nth0(I1,I2,Mat,E):-nth0(I1,Mat,Ls),nth0(I2,Ls,E).
sum(X,Y,Z) :- Z is X + Y.
empty_List(List) :- List = [].
notempty_List(List) :- List \\== [].


has_queen(Ls,F,N):-(N=:=0,F =:=0,empty_List(Ls)  );
    (N>0,F=:=0,notempty_List(Ls),nth0(0,Ls, E,R),  isnot_queen(E),has_queen(R,0,N-1) );
    (N>0,F=:=1,notempty_List(Ls),nth0(0,Ls, E,R),  isnot_queen(E),has_queen(R,1,N-1) );
    (N>0 , F=:=1,nth0(0,Ls, E,R),  is_queen(E),has_queen(R,0,N-1) ).
    
is_nqueens(Mat,N):-(N=:=1,nth0(0,Mat, E),has_queen(E,1,8));
    (N >1, nth0(0,Mat, E, R),has_queen(E,1,8),is_nqueens(R,N-1)  ).
is_vertical_valid(Mat,I1,I2,F):-(I2=:=8);
    ( I1=:=8 ,F=:=0,sum(I2,1,I2n),is_vertical_valid(Mat,0,I2n,1));
    ( I1<8 ,is_nth0nth0(I1,I2,Mat,E),(   ( isnot_queen(E)  ,sum(I1,1,I1n),is_vertical_valid(Mat,I1n,I2,F));(F=:=1, is_queen(E)  ,sum(I1,1,I1n),is_vertical_valid(Mat,I1n,I2,0)  ))).

positions([[0,0],[1,0],[2,0],[3,0],[4,0],[5,0],[6,0],[7,0],[7,1],[7,2],[7,3],[7,4],[7,5],[7,6],[7,7]]).

validate_diagonal(Mat,I1,I2,Cnt):-(   (   I2=:=8;I1 =:= -1),Cnt<2);
    (  is_nth0nth0(I1,I2,Mat,E),sum(I1,-1,I1n),sum(I2,1,I2n),(   (  isnot_queen(E) ,validate_diagonal(Mat,I1n,I2n,Cnt)  );(   is_queen(E) ,sum(Cnt,1,Cntn),validate_diagonal(Mat,I1n,I2n,Cntn)  )) ).
    
is_diagonal_valid(Mat,Crp):-(Crp =:=15);
   	(Crp<15,positions(Pos),nth0(Crp,Pos,I),nth0(0,I,I1),nth0(1,I,I2), validate_diagonal(Mat,I1,I2,0),sum(Crp,1,Crpn),is_diagonal_valid(Mat,Crpn)  ).
   
% is_nqueens(Ls,8),is_vertical_valid(Ls,0,0,1),is_diagonal_valid(Ls,0).`




