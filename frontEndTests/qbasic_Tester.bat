::Batch file to test the QBASIC Front-end
::Root path is Cisc327/src/frontEndTests

@echo off

goto :main

:main
	cd inputs
	SET list=Login Logout Createacct Deleteacct Deposit Withdraw Transfer
	FOR %%i IN (%list%) DO (
		cd %%i
		echo.
		echo STARTING TESTS FOR %%i
		echo.
		SET count=1
		FOR %%k IN (*) DO ( call :subroutine %%k %%i)
		cd ..
	)
	cd ..
	goto :eof

:subroutine
	cd ..\..
	echo Running test %2 - %count%
	echo.
	echo %count% : %1 : %2
	java -jar qbasic.jar activeAccts.txt outputs\%2\%1 <inputs\%2\%1 >outputs\%2\%count%.log
	cd inputs\%2
	Pause
	echo ----------------------------------------------------------------------
	SET /A count+=1
	
goto :eof