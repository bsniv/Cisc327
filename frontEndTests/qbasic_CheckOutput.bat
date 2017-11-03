::Batch file to check difference between actual & expected outputs
::Root path is Cisc327/src/frontEndTests

@echo off

goto :main

:main
	cd inputs
	set list=Login Logout Createacct Deleteacct Deposit Withdraw Transfer
	for %%i in (%list%) do (
		cd %%i
		echo.
		echo CHECKING OUTPUTS FOR %%i
		echo.
		set count=1
		for %%k in (*) do ( call :subroutine %%k %%i)
		cd ..
	)
	cd ..
	goto :eof
	
:subroutine
	cd ..\..
	echo Checking output for test %2 - %count%
	echo.
	FC /C outputs\%2\%1 expected\%2\%1
	echo.
	FC /C outputs\%2\%count%.log expected\%2\%count%.log
	cd inputs\%2
	pause
	echo ------------------------------------------------------------------------------
	set /A count+=1
	
goto :eof
	
	